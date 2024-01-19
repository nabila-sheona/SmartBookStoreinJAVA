SmartBookStore

Project Overview:

SmartBookStore is designed to revolutionize the traditional library experience. In this project, it is aimed to create an efficient and user-friendly system that seamlessly integrates book management for both users and administrators.
Traditional libraries often face challenges in managing their book inventory and providing a smooth borrowing and returning process. SmartBookStore addresses these challenges by introducing a complete set of features, including user membership management, real-time book
avallability display, and streamlined checkout processes
Through the use of modern technologies (Java), a robust system is tried to be developed which leverages a console-based user interface. This approach ensures simplicity and accessibility for users and administrators the same way.

Classes and responsibilities:

1. Library Class:
a. Manages the overall functionality of the library.
b. Handles book-related operations such as adding, removing, and displaying books.
c. Tracks book data and maintains lists of available, borrowed, and sold books.
d. Implements ILibraryService interface.

2. Main Class:
a. Contains the main method to initiate the program.
b. nitializes instances of various services and modules.

3.	Pair Class:
a.	A generic class to represent a pair of values.
b.	Used to store pairs of strings (e.g., book title and author).

4.	SmartBookstore Class:
a.	Represents the main application.
b.	Handles user interactions and menu options.
c.	Comprises instances of different modules and services.
d.	Implements Dependency Inversion Principle (DIP).

5.	UserModule Class:
a.	Manages user-specific functionalities.
b.	Allows users to browse books, borrow, return, add to cart, and checkout.
c.	Implements IUserService.

6.	UserService Class:
a.	Handles user-related operations such as borrowing and returning books.
b.	Manages the list of borrowed books for a user.
c.	Implements IUserService and follows Dependency Inversion Principle (DIP).

7.	AdminModule Class:
a.	Manages admin-specific functionalities.
b.	Allows admins to perform operations like adding and removing books.
c.	Implements IAdminService.

8.	AdminService Class:
a.	Implements admin-related operations.
b.	Allows admins to add, remove, and manipulate books in the library.
c.	Implements IAdminService.

9.	BorrowReturnService Class:
a.	Manages book borrowing and returning processes.
b.	Implements IBorrowReturnService.
c.	Follows Dependency Inversion Principle (DIP).

10.	AddToCart Class:
a.	Handles the addition of books to the cart.
b.	Implements IAddToCart and interacts with the library service.
c.	Follows Dependency Inversion Principle (DIP).

11.	GenerateNewUser Class:
a.	Represents the functionality to generate new library users.
b.	Implements IGenerateNewUser.

12.	Book Class:
a.	Represents the structure of a book, including its title, author, genre, price, quantity, and borrowed status.
b.	Used to create instances of books within the library system. 


