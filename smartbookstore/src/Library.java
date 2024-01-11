//import javafx.util.Pair;
import java.io.*;
import java.util.*;
import java.util.stream.Collectors;
import java.io.File;
import java.nio.file.Files;
import java.nio.file.Paths;



public class Library implements ILibraryService {

    private final List<Pair<String, String>> borrowedBooks = new ArrayList<>();


    private final List<Book> Books = new ArrayList<>();
    private final List<Book> ShoppingCart = new ArrayList<>();
    private final List<Book> SoldBooks = new ArrayList<>();
    private final String filePath = "BookData.txt";
    private final String soldBooksFilePath = "SoldBooksData.txt";

    // Constructor: Load existing book data when initializing the Library
    public Library() {
        loadBookData();
        loadSoldBooksData();
    }



    public List<Book> getAvailableBooksByGenre(Genre genre) {
        return Books.stream().filter(book -> book.getQuantity() > 0 && book.getBookGenre() == genre)
                .collect(Collectors.toList());
    }

    public void displayBooksByGenre() {
        System.out.println("Enter the genre (Fiction, Mystery, Scifi, Romance, Classic, Fantasy, NonFiction, Youngadult, etc.):");
        Scanner scanner = new Scanner(System.in);
        String genreInput = scanner.nextLine();

        Genre genre;
        try {
            genre = Genre.valueOf(genreInput);
        } catch (IllegalArgumentException e) {
            System.out.println("Invalid genre. Please enter a valid genre.");
            return;
        }

        System.out.println("All Books:");
        getAvailableBooksByGenre(genre).forEach(book ->
                System.out.printf("%s by %s - $%.2f (Available: %d)%n", book.getTitle(), book.getAuthor(),
                        book.getPrice(), book.getQuantity()));
    }

    public void displayAllBooks() {
        System.out.println("All Books:");
        getAvailableBooks().forEach(book ->
                System.out.printf("%s by %s - $%.2f (Available: %d)%n", book.getTitle(), book.getAuthor(),
                        book.getPrice(), book.getQuantity()));
    }

    public void displayAvailableBooks() {
        System.out.println("\nAvailable Books:");
        getAvailableBooks().forEach(book ->
                System.out.printf("%s by %s - $%.2f (Available: %d)%n", book.getTitle(), book.getAuthor(),
                        book.getPrice(), book.getQuantity()));
    }

    public void addBookToLibrary(String title, String author, Genre bookGenre, double price, int quantity) {
        // Check if the book already exists in the library
        Book existingBook = Books.stream()
                .filter(book ->
                        book.getTitle().equalsIgnoreCase(title) &&
                                book.getAuthor().equalsIgnoreCase(author) &&
                                book.getBookGenre() == bookGenre &&
                                Double.compare(book.getPrice(), price) == 0)
                .findFirst()
                .orElse(null);

        if (existingBook != null) {
            // If the book exists, increase the quantity
            existingBook.setQuantity(existingBook.getQuantity() + quantity);
            System.out.printf("%d more copies of '%s' by %s added to the library. Total quantity: %d%n",
                    quantity, title, author, existingBook.getQuantity());
        } else {
            // If the book doesn't exist, add a new entry
            Book newBook = new Book(title, author, bookGenre, price, quantity);
            Books.add(newBook);
            System.out.printf("%d copies of '%s' by %s added to the library.%n", quantity, title, author);
        }

        // Save the updated book data to the text file after adding or updating books
        saveBookData();
    }


    public List<Book> getAvailableBooks() {
        return Books.stream().filter(book -> book.getQuantity() > 0).collect(Collectors.toList());
    }



    public void loadBookData() {
        try {
            if (new File(filePath).exists()) {
                // Read existing book data from the text file
                List<String> lines = Files.readAllLines(Paths.get(filePath));

                for (String line : lines) {
                    String[] parts = line.split(",");
                    if (parts.length == 6) {
                        Book book = new Book(
                                parts[0],
                                parts[1],
                                Genre.valueOf(parts[2]),
                                Double.parseDouble(parts[3]),
                                Integer.parseInt(parts[4])
                               // , Boolean.parseBoolean(parts[5])
                        );
                        Books.add(book);
                    }
                }
            }
        } catch (IOException ex) {
            System.out.println("Error loading book data: " + ex.getMessage());
        }
    }

    public void saveBookData() {
        try {
            FileWriter writer = new FileWriter(filePath);
            for (Book book : Books) {
                writer.write(String.format("%s,%s,%s,%.2f,%d,%b%n",
                        book.getTitle(), book.getAuthor(), book.getBookGenre(), book.getPrice(),
                        book.getQuantity(), book.isBorrowed()));
            }
            writer.close();
            System.out.println("Book data saved successfully.");
        } catch (IOException ex) {
            System.out.println("Error saving book data: " + ex.getMessage());
        }
    }

    private void loadSoldBooksData() {
        try {
            if (new File(soldBooksFilePath).exists()) {
                List<String> lines = Files.readAllLines(Paths.get(soldBooksFilePath));
                for (String line : lines) {
                    String[] values = line.split(",");
                    if (values.length == 6) {
                        SoldBooks.add(new Book(
                                values[0],
                                values[1],
                                Genre.valueOf(values[2]),
                                Double.parseDouble(values[3]),
                                Integer.parseInt(values[4]) //,
                                //Boolean.parseBoolean(values[5])
                        ));
                    }
                }
            }
        } catch (IOException ex) {
            System.out.println("Error loading sold books data: " + ex.getMessage());
        }
    }

    private void saveSoldBooksData(double totalBill) {
        try {
            FileWriter writer = new FileWriter(soldBooksFilePath);
            for (Book book : SoldBooks) {
                writer.write(String.format("%s,%s,%s,%.2f,%d,%b%n",
                        book.getTitle(), book.getAuthor(), book.getBookGenre(), book.getPrice(),
                        book.getQuantity(), book.isBorrowed()));
            }
            // Append total bill to the file
            writer.write(String.format("TotalBill,%.2f%n", totalBill));
            writer.close();
        } catch (IOException ex) {
            System.out.println("Error saving sold books data: " + ex.getMessage());
        }
    }

    public void addInCart(String title, int quantity) {
        // Check if the book exists in the library
        Book existingBook = Books.stream()
                .filter(book -> book.getTitle().equalsIgnoreCase(title))
                .findFirst()
                .orElse(null);

        if (existingBook != null && existingBook.getQuantity() >= quantity) {
            // If the book exists and there are enough copies, add to the cart
            Book cartItem = new Book(
                    existingBook.getTitle(),
                    existingBook.getAuthor(),
                    existingBook.getBookGenre(),
                    existingBook.getPrice(),
                    quantity //,
                    //false
            );

            ShoppingCart.add(cartItem);
            System.out.printf("%d copies of '%s' added to the cart.%n", quantity, title);
        } else {
            System.out.printf("Not enough copies of '%s' available to add to the cart.%n", title);
        }
    }

    public void checkout() {
        double totalBill = 0;

        // Check out the items in the cart and update book quantities
        for (Book cartItem : ShoppingCart) {
            Book existingBook = Books.stream()
                    .filter(book -> book.getTitle().equalsIgnoreCase(cartItem.getTitle()))
                    .findFirst()
                    .orElse(null);

            if (existingBook != null && existingBook.getQuantity() >= cartItem.getQuantity()) {
                // If the book exists and there are enough copies, update the quantity
                existingBook.setQuantity(existingBook.getQuantity() - cartItem.getQuantity());

                // Calculate and accumulate the total bill
                totalBill += cartItem.getPrice() * cartItem.getQuantity();

                // Add the sold book to the soldBooks list
                SoldBooks.add(new Book(
                        existingBook.getTitle(),
                        existingBook.getAuthor(),
                        existingBook.getBookGenre(),
                        existingBook.getPrice(),
                        cartItem.getQuantity() //,
                        //false
                ));

                System.out.printf("%d copies of '%s' checked out successfully. Remaining quantity: %d%n",
                        cartItem.getQuantity(), cartItem.getTitle(), existingBook.getQuantity());
            } else {
                System.out.printf("Not enough copies of '%s' available to check out.%n", cartItem.getTitle());
            }
        }

        // Display and save the total bill
        System.out.printf("Total Bill: $%.2f%n", totalBill);

        // Clear the cart after checkout
        ShoppingCart.clear();

        // Save the updated book data, sold books data, and total bill to the text files after checkout
        saveBookData();
        saveSoldBooksData(totalBill);
    }

    public void removeBookFromLibrary(String titleToRemove) {
        Book bookToRemove = Books.stream()
                .filter(book -> book.getTitle().equalsIgnoreCase(titleToRemove))
                .findFirst()
                .orElse(null);

        if (bookToRemove != null) {
            Books.remove(bookToRemove);

            // Save the updated book data to the text file after removing
            saveBookData();
            System.out.printf("Book '%s' removed from the library.%n", titleToRemove);
        } else {
            System.out.printf("Book '%s' not found in the library.%n", titleToRemove);
        }
    }

    public void decreaseBookQuantity(String bookTitle) {
        Book availableBook = getAvailableBooks().stream()
                .filter(book -> book.getTitle().equalsIgnoreCase(bookTitle))
                .findFirst()
                .orElse(null);

        if (availableBook != null && availableBook.getQuantity() > 0) {
            // Decrease the quantity for the book
            availableBook.setQuantity(availableBook.getQuantity() - 1);

            // Save the updated book data to the text file after borrowing
            saveBookData();

            System.out.printf("Borrowed 1 copy of '%s'. Remaining quantity: %d%n", availableBook.getTitle(), availableBook.getQuantity());
        } else {
            System.out.printf("Book '%s' not available for borrowing.%n", bookTitle);
        }
    }

    public void increaseBookQuantity(String bookTitle) {
        Book availableBook = getAvailableBooks().stream()
                .filter(book -> book.getTitle().equalsIgnoreCase(bookTitle))
                .findFirst()
                .orElse(null);

        if (availableBook != null && availableBook.getQuantity() > 0) {
            // Increase the quantity for the book
            availableBook.setQuantity(availableBook.getQuantity() + 1);

            // Save the updated book data to the text file after borrowing
            saveBookData();

            System.out.printf("Borrowed 1 copy of '%s'. Remaining quantity: %d%n", availableBook.getTitle(), availableBook.getQuantity());
        } else {
            System.out.printf("Book '%s' not available for borrowing.%n", bookTitle);
        }
    }
}
