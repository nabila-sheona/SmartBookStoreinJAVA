import java.util.List;

public interface IUserService {
    // void becomeMember();
    List<Book> getBorrowedBooks();

    void returnBook(Book book, String userId);
    void borrowBook(Book book, String userId);
    // boolean isLibraryMember();
}
