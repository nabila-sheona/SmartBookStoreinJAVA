import java.util.Scanner;
import java.util.List;
import java.util.ArrayList;

public class AdminService implements IAdminService {
    private final List<Book> books;
    private final ILibraryService libraryService;

    public AdminService(ILibraryService libraryService) {
        this.books = new ArrayList<>();
        this.libraryService = libraryService;
    }

    @Override
    public void addBookToLibrary() {
        Scanner scanner = new Scanner(System.in);

        System.out.println("\nEnter the details of the new book:");
        System.out.print("Enter the title: ");
        String title = scanner.nextLine();

        if (title == null || title.trim().isEmpty()) {
            System.out.println("Invalid title. Please enter a valid title.");
            return;
        }

        System.out.print("Enter the author: ");
        String author = scanner.nextLine();

        if (author == null || author.trim().isEmpty()) {
            System.out.println("Invalid author. Please enter a valid author.");
            return;
        }

        Genre genre = null;

        System.out.println("Enter the genre (Fiction, Mystery, Scifi, Romance, Classic, Fantasy, NonFiction, Youngadult, etc.):");
        String genreInput = scanner.nextLine();

        try {
            genre = Genre.valueOf(genreInput);
            // Continue processing with the valid genre
        } catch (IllegalArgumentException e) {
            System.out.println("Invalid genre. Please enter a valid genre.");
            return;
        }


        System.out.print("Enter the price: ");

        try {
            double price = Double.parseDouble(scanner.nextLine());
            if (price > 0) {
                System.out.print("Enter the quantity: ");

                try {
                    int quantity = Integer.parseInt(scanner.nextLine());
                    if (quantity > 0) {
                        libraryService.addBookToLibrary(title, author, genre, price, quantity);
                        System.out.println(quantity + " copies of '" + title + "' by " + author + " added to the library.");
                    } else {
                        System.out.println("Invalid quantity. Please enter a valid number greater than 0.");
                    }
                } catch (NumberFormatException e) {
                    System.out.println("Invalid quantity format. Please enter a valid number greater than 0.");
                }
            } else {
                System.out.println("Invalid price. Please enter a valid number greater than 0.");
            }
        } catch (NumberFormatException e) {
            System.out.println("Invalid price format. Please enter a valid number greater than 0.");
        }
    }

    @Override
    public void removeBookFromLibrary() {
        Scanner scanner = new Scanner(System.in);

        System.out.println("Book to delete: ");
        String title = scanner.nextLine();
        libraryService.removeBookFromLibrary(title);
    }
}
