import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Scanner;

import DATALAYER.DatabaseManager;

public class App {
    DatabaseManager Db=new  DatabaseManager();

    private Connection con =Db.getConnection();

    public App(Connection con) {
        this.con = con;
    }

    public App() {
    }

    public void createBookFromUserInput() {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Enter Title:");
        String title = scanner.nextLine();
        System.out.println("Enter Author ID:");
        int authorId = scanner.nextInt();
        System.out.println("Enter Genre ID:");
        int genreId = scanner.nextInt();
        scanner.nextLine();
        System.out.println("Enter Publication Date (YYYY-MM-DD):");
        String publicationDate = scanner.nextLine();

        if (!isValidBookData(title, authorId, genreId, publicationDate)) {
            System.out.println("Invalid input parameters for creating book.");
            return;
        }

        String query = "INSERT INTO books (title, author_id, genre_id, publication_date) VALUES ('" + title + "','"
                + authorId + "','" + genreId + "','" + publicationDate + "')";
        try (Statement statement = con.createStatement()) {
            int rowsInserted = statement.executeUpdate(query, Statement.RETURN_GENERATED_KEYS);
            if (rowsInserted > 0) {
                ResultSet generatedKeys = statement.getGeneratedKeys();
                if (generatedKeys.next()) {
                    int bookId = generatedKeys.getInt(1);
                    System.out.println("Book created successfully with ID: " + bookId);
                }
            } else {
                System.out.println("Failed to create book.");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private boolean isValidBookData(String title, int authorId, int genreId, String publicationDate) {

        if (title.isEmpty() || authorId <= 0 || genreId <= 0 || publicationDate.isEmpty()) {
            return false;
        }

        return true;
    }

    // Data contract for reading books
    public void readBooks() {
        String query = "SELECT * FROM books";
        try (Statement statement = con.createStatement();
                ResultSet resultSet = statement.executeQuery(query)) {
            while (resultSet.next()) {
                int id = resultSet.getInt("book_id");
                String title = resultSet.getString("title");
                int authorId = resultSet.getInt("author_id");
                int genreId = resultSet.getInt("genre_id");
                String publicationDate = resultSet.getString("publication_date");
                boolean deleted = resultSet.getBoolean("deleted");
                System.out.println("ID: " + id + ", Title: " + title + ", Author ID: " + authorId + ", Genre ID: "
                        + genreId + ", Publication Date: " + publicationDate + ", Deleted: " + deleted);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // Data contract for updating a book
    public void updateBookFromUserInput() {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Enter Book ID to update:");
        int bookId = scanner.nextInt();
        scanner.nextLine(); // Consume newline
        System.out.println("Enter New Title:");
        String title = scanner.nextLine();
        System.out.println("Enter New Author ID:");
        int authorId = scanner.nextInt();
        System.out.println("Enter New Genre ID:");
        int genreId = scanner.nextInt();
        scanner.nextLine(); // Consume newline
        System.out.println("Enter New Publication Date (YYYY-MM-DD):");
        String publicationDate = scanner.nextLine();

        if (bookId <= 0 || title.isEmpty() || authorId <= 0 || genreId <= 0 || publicationDate.isEmpty()) {
            System.out.println("Invalid input parameters for updating book.");
            return;
        }

        String query = "UPDATE books SET title='" + title + "', author_id=" + authorId + ", genre_id=" + genreId
                + ", publication_date='" + publicationDate + "' WHERE book_id=" + bookId;
        try (Statement statement = con.createStatement()) {
            int rowsUpdated = statement.executeUpdate(query);
            if (rowsUpdated > 0) {
                System.out.println("Book updated successfully!");
            } else {
                System.out.println("Failed to update book.");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void deleteBookFromUserInput() {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Enter Book ID to delete:");
        int bookId = scanner.nextInt();

        if (bookId <= 0) {
            System.out.println("Invalid input parameter for deleting book.");
            return;
        }

        String query = "DELETE FROM books WHERE book_id=" + bookId;
        try (Statement statement = con.createStatement()) {
            int rowsDeleted = statement.executeUpdate(query);
            if (rowsDeleted > 0) {
                System.out.println("Book deleted successfully!");
            } else {
                System.out.println("Failed to delete book.");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) throws Exception {
        App app = new App();
        Scanner scanner = new Scanner(System.in);
        int choice;
        do {
            System.out.println("\n1. Create Book");
            System.out.println("2. Read Books");
            System.out.println("3. Update Book");
            System.out.println("4. Delete Book");
            System.out.println("5. Exit");
            System.out.println("Enter your choice:");
            choice = scanner.nextInt();
            scanner.nextLine(); // Consume newline

            switch (choice) {
                case 1:
                    app.createBookFromUserInput();
                    break;
                case 2:
                    app.readBooks();
                    break;
                case 3:
                    app.updateBookFromUserInput();
                    break;
                case 4:
                    app.deleteBookFromUserInput();
                    break;
                case 5:
                    System.out.println("Exiting program...");
                    break;
                default:
                    System.out.println("Invalid choice. Please enter a number from 1 to 5.");
            }
        } while (choice != 5);
        DatabaseManager.closeConnection();
    }
}
