import java.util.Scanner;

public class AddToCart implements IAddToCart {
    private final ILibraryService libraryService;

    public AddToCart(ILibraryService libraryService) {
        this.libraryService = libraryService;
    }

    @Override
    public void addBookToCart(ILibraryService libraryService) {
        Scanner scanner = new Scanner(System.in);

        System.out.println("\nEnter the title of the book you want to add to the cart:");
        String title = scanner.nextLine();

        if (title == null || title.trim().isEmpty()) {
            System.out.println("Invalid title. Please enter a valid title.");
            return;
        }

        System.out.println("Enter the quantity:");

        try {
            int quantity = Integer.parseInt(scanner.nextLine());

            if (quantity > 0) {
                libraryService.addInCart(title, quantity);
                System.out.println(quantity + " copies of '" + title + "' added to the cart.");
            } else {
                System.out.println("Invalid quantity. Please enter a valid number greater than 0.");
            }
        } catch (NumberFormatException e) {
            System.out.println("Invalid quantity format. Please enter a valid number greater than 0.");
        }
    }
}

