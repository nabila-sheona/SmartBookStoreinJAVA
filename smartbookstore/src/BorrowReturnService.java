import java.util.List;
import java.util.Scanner;

public class BorrowReturnService implements IBorrowReturnService {
    private final IUserService userService;
    private final ILibraryService libraryService;
    private final ICheckUserId checkUserService;

    public BorrowReturnService(IUserService userService, ILibraryService libraryService, ICheckUserId checkUserService) {
        this.userService = userService;
        this.libraryService = libraryService;
        this.checkUserService = checkUserService;
    }

    @Override
    public void borrowBook() {
        Scanner scanner = new Scanner(System.in);

        System.out.println("Are you a library member? (yes/no)");
        String response = scanner.nextLine();

        if ("yes".equalsIgnoreCase(response)) {
            System.out.println("Enter your user ID:");
            String userId = scanner.nextLine();

            boolean isValidUserId = checkUserService.checkUserId(userId);

            if (isValidUserId) {
                System.out.println("\nEnter the title of the book you want to borrow:");
                String title = scanner.nextLine();
                List<Book> availableBooks = libraryService.getAvailableBooks();
                Book selectedBook = availableBooks.stream()
                        .filter(book -> book.getTitle().equalsIgnoreCase(title))
                        .findFirst()
                        .orElse(null);

                if (selectedBook != null && selectedBook.getQuantity() > 0) {
                    userService.borrowBook(selectedBook, userId);
                    System.out.println("You have successfully borrowed '" + selectedBook.getTitle() + "'.");
                } else {
                    System.out.println("Sorry, the selected book is not available for borrowing.");
                }
            } else {
                System.out.println("Invalid user ID. Please enter a valid user ID.");
            }
        }
        // ... (code for 'no' and other cases)
    }

    @Override
    public void returnBook() {
        Scanner scanner = new Scanner(System.in);

        System.out.println("\nUserID: ");
        String userId = scanner.nextLine();
        boolean isValidUserId = checkUserService.checkUserId(userId);

        if (isValidUserId && !userService.getBorrowedBooks().isEmpty()) {
            System.out.println("\nSelect a book to return:");
            int index = 1;
            for (Book book : userService.getBorrowedBooks()) {
                System.out.println(index + ". " + book.getTitle());
                index++;
            }

            if (scanner.hasNextInt()) {
                int returnChoice = scanner.nextInt();
                if (returnChoice >= 1 && returnChoice <= userService.getBorrowedBooks().size()) {
                    Book returningBook = userService.getBorrowedBooks().get(returnChoice - 1);
                    userService.returnBook(returningBook, userId);
                    System.out.println("You have successfully returned '" + returningBook.getTitle() + "'.");
                } else {
                    System.out.println("Invalid choice. Please enter a valid number.");
                }
            } else {
                System.out.println("Invalid choice. Please enter a valid number.");
            }
        } else {
            System.out.println("You haven't borrowed any books yet.");
        }
    }
}


