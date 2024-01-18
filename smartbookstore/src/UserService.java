import java.nio.file.StandardOpenOption;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Scanner;
import java.nio.file.Files;
import java.nio.file.Path;
import java.text.ParseException;

public class UserService implements IUserService {
    private boolean isLibraryMember;
    private List<Book> borrowedBooks = new ArrayList<>();
    private ILibraryService libraryService;
    private String userId;

    public UserService(ILibraryService libraryService) {
        this.libraryService = libraryService;
        // LoadUserInformationFromFile();
    }
    @Override
    public List<Book> getBorrowedBooks() {
        return borrowedBooks;
    }
    private void saveBorrowedBooksData(Book book, String userId, boolean isReturned) {
        // Specify the path to your borrowed books data file
        String borrowedBooksFilePath = "BorrowedBooksData.txt";

        try {
            // Create or append to the borrowed books data file
            FileWriter writer = new FileWriter(borrowedBooksFilePath, true);

            // Save book information along with borrowing date, user ID, and is returned status
            String line = String.format("%s,%s,%s,%s%n", book.getTitle(),
                    new SimpleDateFormat("yyyy-MM-dd").format(new Date()), userId, isReturned);

            // Write the line to the file
            writer.write(line);

            // Close the writer
            writer.close();

            System.out.println("Book data saved.");
        } catch (IOException ex) {
            // Handle any exceptions (e.g., file not found, permission issues)
            System.out.println("Error saving borrowed books data: " + ex.getMessage());
        }
    }

    @Override
    public void borrowBook(Book book, String userId) {
        if (book.isAvailable()) {
            book.borrowBook(); // This method should set IsBorrowed to true
            saveBorrowedBooksData(book, userId, false);
            String borrowed = book.getTitle();
            libraryService.decreaseBookQuantity(borrowed);
            borrowedBooks.add(book);
            System.out.println("You have successfully borrowed '" + book.getTitle() + "'.");
        } else {
            System.out.println("Unable to borrow '" + book.getTitle() + "'.");
        }
    }

    @Override
    public void returnBook(Book book, String userId) {
        if (book.isBorrowed()) {
            String borrowed = book.getTitle();
            libraryService.increaseBookQuantity(borrowed);

            // Calculate the number of days the book has been borrowed
            int daysBorrowed = calculateDaysBorrowed(book);

            // Calculate the amount to be paid
            double amountToPay = calculateAmountToPay(daysBorrowed);
            book.setBorrowed(false);

            // Add the book back to the library

            // Update the list of borrowed books

            System.out.println("You have successfully returned '" + book.getTitle() + "'.");
            System.out.println("Amount to pay: $" + amountToPay);

            // Save the updated list of borrowed books to the user's file
            saveBorrowedBooksData(book, userId, true);

            // Update the book's availability

        } else {
            System.out.println("You haven't borrowed '" + book.getTitle() + "'.");
        }
    }

    private int calculateDaysBorrowed(Book book) {
        // Specify the path to your borrowed books data file
        String borrowedBooksFilePath = "BorrowedBooksData.txt";

        try {
            // Read all lines from the borrowed books data file
            List<String> lines = Files.readAllLines(Path.of(borrowedBooksFilePath));

            for (String line : lines) {
                // Split the line into parts
                String[] parts = line.split(",");

                // Extract the book title, borrowing date, and user ID
                String bookTitle = parts[0].trim();
                Date borrowingDate = new SimpleDateFormat("yyyy-MM-dd").parse(parts[1].trim());
                String userId = parts[2].trim();

                // Check if the current line corresponds to the given book
                if (bookTitle.equalsIgnoreCase(book.getTitle())) {
                    // Calculate the number of days between the borrowing date and today
                    long diff = new Date().getTime() - borrowingDate.getTime();
                    int daysBorrowed = (int) (diff / (24 * 60 * 60 * 1000));

                    // Return the calculated number of days
                    return daysBorrowed;
                }
            }

            // If no match is found, return a default value (0)
            return 0;
        } catch (IOException | ParseException ex) {
            // Handle any exceptions (e.g., file not found, permission issues)
            System.out.println("Error calculating days borrowed: " + ex.getMessage());

            // Return a default value (0)
            return 0;
        }
    }

    private double calculateAmountToPay(int daysBorrowed) {
        double dailyRate = 0.5;
        double additionalRate = 1.0;

        if (daysBorrowed <= 15) {
            return dailyRate * daysBorrowed;
        } else {
            return (dailyRate * 15) + (additionalRate * (daysBorrowed - 15));
        }
    }


}
