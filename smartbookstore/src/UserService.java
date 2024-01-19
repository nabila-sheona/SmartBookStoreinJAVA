import java.nio.charset.Charset;
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
import java.nio.file.Paths;

public class UserService implements IUserService {
    private boolean isLibraryMember;
    private List<Book> borrowedBooks = new ArrayList<>();
    private ILibraryService libraryService;
    private String userId;

    public UserService(ILibraryService libraryService) {
        this.libraryService = libraryService;
        loadBorrowedBooksData();
    }
    @Override
    public List<Book> getBorrowedBooks() {
        return borrowedBooks;
    }

    private void loadBorrowedBooksData() {
        // Specify the path to your borrowed books data file
        String borrowedBooksFilePath = "BorrowedBooksData.txt";

        try {
            if (Files.exists(Path.of(borrowedBooksFilePath))) {
                // Read all lines from the borrowed books data file
                List<String> lines = Files.readAllLines(Path.of(borrowedBooksFilePath));

                for (String line : lines) {
                    // Split the line into parts
                    String[] parts = line.split(",");

                    // Extract the book title, borrowing date, user ID, and is returned status
                    String bookTitle = parts[0].trim();
                    Date borrowingDate = new SimpleDateFormat("yyyy-MM-dd").parse(parts[1].trim());
                    String userId = parts[2].trim();
                    boolean isReturned = Boolean.parseBoolean(parts[3].trim());

                    // Create a Book object
                    Book book = new Book(bookTitle, "", Genre.Fiction, 0.0, 0);
                    book.setBorrowed(!isReturned);  // If not returned, set as borrowed

                    // Add the book to the borrowedBooks list
                    borrowedBooks.add(book);
                }
            }
        } catch (IOException | ParseException ex) {
            // Handle any exceptions
            System.out.println("Error loading borrowed books data: " + ex.getMessage());
        }
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

            // Remove the book entry from the borrowed books data file
            removeBorrowedBookEntry(book.getTitle());

            // Update the list of borrowed books
            borrowedBooks.remove(book);

            System.out.println("You have successfully returned '" + book.getTitle() + "'.");
            System.out.println("Amount to pay: $" + amountToPay);
        } else {
            System.out.println("You haven't borrowed '" + book.getTitle() + "'.");
        }
    }

    private void removeBorrowedBookEntry(String bookTitle) {
        // Specify the path to your borrowed books data file
        String borrowedBooksFilePath = "BorrowedBooksData.txt";

        try {
            List<String> lines = Files.readAllLines(Path.of(borrowedBooksFilePath));

            // Remove the entry corresponding to the returned book
            lines.removeIf(line -> line.startsWith(bookTitle + ","));

            // Write the updated lines back to the file
            Files.write(Path.of(borrowedBooksFilePath), lines, Charset.forName("UTF-8"));
        } catch (IOException ex) {
            System.out.println("Error removing borrowed book entry: " + ex.getMessage());
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
                    int hour=24;
                    int minute=60;
                    int second=60;
                    int factor=1000;
                    int daysBorrowed = (int) (diff / (hour * minute * second * factor));

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
        int dayslimit=15;
        if (daysBorrowed <= dayslimit) {
            return dailyRate * daysBorrowed;
        } else {
            return (dailyRate * dayslimit) + (additionalRate * (daysBorrowed - dayslimit));
        }
    }


}
