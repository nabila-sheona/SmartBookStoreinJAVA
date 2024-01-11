import java.util.Scanner;

public class UserModule {
    private final IUserService userService;
    private final ILibraryService libraryService;
    private final ICheckUserId checkUserIdService;
    private final IBorrowReturnService borrowReturnService;
    private final IAddToCart addToCartService;
    private final IGenerateNewUser generateNewUserService;

    public UserModule(IUserService userService, ILibraryService libraryService,
                      ICheckUserId checkUserIdService, IBorrowReturnService borrowReturnService,
                      IAddToCart addToCartService, IGenerateNewUser generateNewUserService) {
        this.userService = userService;
        this.libraryService = libraryService;
        this.checkUserIdService = checkUserIdService;
        this.borrowReturnService = borrowReturnService;
        this.addToCartService = addToCartService;
        this.generateNewUserService = generateNewUserService;
    }

    public void userRun() {
        Scanner scanner = new Scanner(System.in);

        while (true) {
            System.out.println("\nSelect an option:");
            System.out.println("1. Browse available books");
            System.out.println("2. Browse available books by genre");
            System.out.println("3. Become a library member");
            System.out.println("4. Borrow a book");
            System.out.println("5. Return a book");
            System.out.println("6. Add to cart");
            System.out.println("7. Checkout");
            System.out.println("8. Exit");

            if (scanner.hasNextInt()) {
                int choice = scanner.nextInt();
                scanner.nextLine(); // Consume the newline character

                switch (choice) {
                    case 1:
                        libraryService.displayAvailableBooks();
                        break;

                    case 2:
                        libraryService.displayBooksByGenre();
                        break;

                    case 3:
                        generateNewUserService.becomeMember();
                        System.out.println("You are now a library member!");
                        break;

                    case 4:
                        borrowReturnService.borrowBook();
                        break;

                    case 5:
                        borrowReturnService.returnBook();
                        break;

                    case 6:
                        addToCartService.addBookToCart(libraryService);
                        break;

                    case 7:
                        libraryService.checkout();
                        break;

                    case 8:
                        System.out.println("Thank you for using our Bookstore. Goodbye!");
                        System.exit(0);
                        break;

                    default:
                        System.out.println("Invalid choice. Please try again.");
                        break;
                }
            } else {
                System.out.println("Invalid input. Please enter a number.");
                scanner.nextLine(); // Consume the invalid input
            }
        }
    }
}
