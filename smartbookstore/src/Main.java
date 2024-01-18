import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        ILibraryService libraryService =
                new Library();
        IUserService userService = new UserService(libraryService);
        IAdminService adminService = new AdminService(libraryService);
        ICheckUserId checkUserIdService = new CheckUserID();
        IAddToCart addToCartService = new AddToCart(libraryService);
        IGenerateNewUser generateNewUserService = new GenerateNewUser();
        IBorrowReturnService borrowReturnService = new BorrowReturnService(userService, libraryService, checkUserIdService);

        SmartBookstore smartBookstore = new SmartBookstore(userService, libraryService, adminService, checkUserIdService, borrowReturnService, addToCartService, generateNewUserService);
        smartBookstore.run();
    }
}
