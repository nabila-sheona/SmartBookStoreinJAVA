public interface IBookService {
    void borrowBook(UserService user, Book book);
    void returnBook(UserService user, Book book);
    double calculateBorrowingFee(Book book);
    void sellBook(Book book);
}
