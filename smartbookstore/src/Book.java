public class Book {
    private String title;
    private String author;
    private double price;
    private int quantity;
    private boolean isBorrowed;
    private Genre bookGenre;

    public Book(String title, String author, Genre bookGenre, double price, int quantity) {
        this.title = title;
        this.author = author;
        this.price = price;
        this.quantity = quantity;
        this.isBorrowed = false;
        this.bookGenre = bookGenre;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getAuthor() {
        return author;
    }
    public void setBorrowed(boolean isBorrowed) {
        this.isBorrowed = isBorrowed;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public boolean isBorrowed() {
        return isBorrowed;
    }

    public Genre getBookGenre() {
        return bookGenre;
    }

    public boolean isAvailable() {
        return quantity > 0;
    }

    public void borrowBook() {
        if (isAvailable()) {
            isBorrowed = true;
        }
    }

    public void returnBook() {
        isBorrowed = false;
    }

    public void sellBook() {
        quantity--;
    }
}