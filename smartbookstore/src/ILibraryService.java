import java.util.List;

public interface ILibraryService {
    void displayAvailableBooks();
    void displayAllBooks();
    void displayBooksByGenre();
    void addBookToLibrary(String title, String author, Genre genre, double price, int quantity);
    void addInCart(String title, int quantity);
    //void addToCart(String title, int quantity);
    // void returnBookToLibrary(Book book, String userId);
    void increaseBookQuantity(String bookTitle);
    void decreaseBookQuantity(String bookTitle);
    void checkout();
    void removeBookFromLibrary(String titleToRemove);
    List<Book> getAvailableBooks();
}
