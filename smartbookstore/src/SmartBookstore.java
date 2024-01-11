import java.util.Scanner;

public class SmartBookstore {
    private final AdminModule adminModule;
    private final UserModule userModule;

    public SmartBookstore(IUserService userService, ILibraryService libraryService,
                          IAdminService adminService, ICheckUserId checkUserIdService,
                          IBorrowReturnService borrowReturnService, IAddToCart addToCart,
                          IGenerateNewUser generateNewUser) {
        adminModule = new AdminModule(libraryService, adminService);
        userModule = new UserModule(userService, libraryService, checkUserIdService,
                borrowReturnService, addToCart, generateNewUser);
    }

    public void run() {
        System.out.println("Welcome to the Smart Bookstore!");

        Scanner scanner = new Scanner(System.in);

        while (true) {
            System.out.println("\nSelect an option:");
            System.out.println("1. Admin Module");
            System.out.println("2. User Module");
            System.out.println("3. Exit");

            int choice;
            if (scanner.hasNextInt()) {
                choice = scanner.nextInt();
                scanner.nextLine(); // Consume the newline character

                switch (choice) {
                    case 1:
                        adminModule.runAdminModule();
                        break;
                    case 2:
                        userModule.userRun();
                        break;
                    case 3:
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
