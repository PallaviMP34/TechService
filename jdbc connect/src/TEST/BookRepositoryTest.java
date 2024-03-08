package TEST;

import static org.junit.Assert.*;

import java.sql.SQLException;
import java.util.List;

import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;

import DATALAYER.BookRepository;
import ENTITY.Book;
import ENTITY.Dummy;

public class BookRepositoryTest {

    private static BookRepository bookRepository;

    @BeforeClass
    public static void setUp() {
        bookRepository = new BookRepository();
    }

    @AfterClass
    public static void tearDown() {
    }

    @Test
    public void testGetAllBooks_Positive() {
        try {
            List<Book> books = bookRepository.getAllBooks();
            assertNotNull(books);
            assertTrue(books.size() > 0);
        } catch (SQLException e) {
            fail("Exception thrown: " + e.getMessage());
        }
    }

    @Test
    public void testGetBookById_Positive() {
        try {
            int bookId = 2; // Assuming book with ID 1 exists in the database
            Book book = bookRepository.getBookById(bookId);
            assertNotNull(book);
            assertEquals(bookId, book.getId());
        } catch (SQLException e) {
            fail("Exception thrown: " + e.getMessage());
        }
    }

    @Test
    public void testCreateBook_Positive() {
        try {
            // Dummy book data for testing
            Dummy dummyBook = new Dummy("Test Book", 1, 1, "2024-02-29");
            assertTrue(bookRepository.createBook(dummyBook));
        } catch (SQLException e) {
            fail("Exception thrown: " + e.getMessage());
        }
    }

    @Test
    public void testUpdateBook_Positive() {
        try {
            // Assuming book with ID 1 exists in the database
            int rowsUpdated = bookRepository.updateBook(2, "Updated Title", 1, 1, "2024-02-29");
            assertTrue(rowsUpdated > 0);
        } catch (SQLException e) {
            fail("Exception thrown: " + e.getMessage());
        }
    }

    @Test
    public void testDeleteBook_Positive() {
        try {
        
            int rowsDeleted = bookRepository.deleteBook(52);
            assertTrue(rowsDeleted > 0);
        } catch (SQLException e) {
            fail("Exception thrown: " + e.getMessage());
        }
    }

    // Negative test cases

    @Test
    public void testGetBookById_Negative_InvalidId() {
        try {
            
            int bookId = -1;
            Book book = bookRepository.getBookById(bookId);
            assertNull(book);
        } catch (SQLException e) {
            fail("Exception thrown: " + e.getMessage());
        }
    }


    @Test
    public void testCreateBook_Negative_NullData() {
        try {
            // Passing null book object, which should fail
            assertFalse(bookRepository.createBook(null));
        } catch (SQLException e) {
            fail("Exception thrown: " + e.getMessage());
        }
    }

    @Test
    public void testUpdateBook_Negative_InvalidId() {
        try {
            // Assuming book with ID -1 does not exist in the database
            int rowsUpdated = bookRepository.updateBook(-1, "Updated Title", 1, 1, "2024-02-29");
            assertEquals(0, rowsUpdated);
        } catch (SQLException e) {
            fail("Exception thrown: " + e.getMessage());
        }
    }

    @Test
    public void testDeleteBook_Negative_InvalidId() {
        try {
            
            int rowsDeleted = bookRepository.deleteBook(-1);
            assertEquals(0, rowsDeleted);
        } catch (SQLException e) {
            fail("Exception thrown: " + e.getMessage());
        }
    }

    
}
