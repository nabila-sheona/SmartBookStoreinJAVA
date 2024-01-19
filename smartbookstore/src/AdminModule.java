import java.util.Scanner;

public class AdminModule {
    private final ILibraryService libraryService;
    private final IAdminService adminService;
    public AdminModule(ILibraryService libraryService, IAdminService adminService) {
        this.libraryService = libraryService;
        this.adminService = adminService;
    }

    public void runAdminModule() {
        Scanner scanner = new Scanner(System.in);

        while (true) {
            System.out.println("1. Add a new book to the store");
            System.out.println("2. Remove book from the store");
            System.out.println("3. Browse books");
            System.out.println("4. Exit");

            int choice;
            try {
                choice = Integer.parseInt(scanner.nextLine());

                switch (choice) {
                    case 1:
                        adminService.addBookToLibrary();
                        break;
                    case 2:
                        adminService.removeBookFromLibrary();
                        break;
                    case 3:
                        libraryService.displayAllBooks();
                        break;
                    case 4:
                        System.out.println("Thank you for using our Bookstore. Goodbye!");
                        System.exit(0);
                        break;
                    default:
                        System.out.println("Invalid choice. Please try again.");
                        break;
                }
            } catch (NumberFormatException e) {
                System.out.println("Invalid input. Please enter a number.");
            }
        }
    }
}
