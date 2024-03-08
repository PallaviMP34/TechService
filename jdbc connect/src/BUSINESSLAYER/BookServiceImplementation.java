package BUSINESSLAYER;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import DATALAYER.BookRepository;
import ENTITY.Book;
import ENTITY.Dummy;

public class BookServiceImplementation implements BookService {
    BookRepository bookRepo = new BookRepository();
    int budget = 50;

    @Override
    public List<Book> getAllBooks() throws SQLException {
        return bookRepo.getAllBooks();
    }

    @Override
    public Book getBookById(int bookId) throws SQLException {
        return bookRepo.getBookById(bookId);
    }

    @Override
    public boolean createBook(Dummy book) throws SQLException {
        bookRepo.createBook(book);
        return true;
    }

    @Override
    public int updateBook(int bookId, String title, int author_id, int genre_id, String publication_date,String damaged)
            throws SQLException {
        return bookRepo.updateBook(bookId,title,author_id, genre_id,  publication_date,damaged);
    }

    @Override
    public int deleteBook(int bookId) throws SQLException {
        return bookRepo.deleteBook(bookId);
    }

    public void updateStatus(List<Book> books) throws SQLException {
        for (Book book : books) {
            bookRepo.updateDataStatus(book.getId(), book.getRepairStatus());
        }

    }

    // public List<Book> manageRepair() throws SQLException {

    //     List<Book> books = new ArrayList<>();
    //     List<Book> bookAdd = new ArrayList<>();
    //     books = sortBooks(bookRepo.getRepairBook());

    //     int cost = 0;
    //     for (Book book : books) {
    //         if (!book.getRepairStatus().contains("completed")) {

    //             int level = getSeverity(book);
    //             int repairCost = setPrice(level);
    //             cost += repairCost;
    //             if (cost <= budget) {
    //                 book.setRepairStatus("completed");
    //                 bookAdd.add(book);
    //             } else {
    //                 book.setRepairStatus("progress");
    //                 bookAdd.add(book);
    //             }
    //         }
    //     }
    //     return bookAdd;

    // }

    // public int getSeverity(Book book) {
    //     if (book.getDamaged().contains("Missing pages") || book.getDamaged().contains("Missing cover")) {
    //         return 4;
    //     } else if (book.getDamaged().contains("Water damage")) {
    //         return 3;
    //     } else if (book.getDamaged().contains("Torn pages") || book.getDamaged().contains("Damaged cover")) {
    //         return 2;
    //     } else if (book.getDamaged().contains("Stain on pages")) {
    //         return 1;
    //     }
    //     return 0;
    // }

    // public int setPrice(int level) {
    //     if (level == 4) {
    //         return 50;
    //     } else if (level == 3) {
    //         return 30;
    //     } else if (level == 2) {
    //         return 20;
    //     }
    //     return 10;
    // }

    public int getSeverity(String damageType) throws SQLException {
        return bookRepo.getSeverity(damageType);
    }

    public int setPrice(String damageType) throws SQLException {
        return bookRepo.setPrice(damageType);
    }
  
    public List<Book> manageRepair() throws SQLException {
        List<Book> books = new ArrayList<>();
        List<Book> bookadd =new ArrayList<>();
        books = bookRepo.getRepairBook();

        int cost = 0;
        for (Book book : books) {
            if (!book.getRepairStatus().contains("completed")) {
                //int level = bookRepo.getSeverity(book.getDamaged());
                int repairCost = bookRepo.setPrice(book.getDamaged());
                cost += repairCost;
                if (cost <= budget) {
                    book.setRepairStatus("completed");
                    bookadd.add(book);
                } else {
                    book.setRepairStatus("progress");
                    bookadd.add(book);
                }
            }
        }
        return books;
    }
    public List<Book> sortBooks(List<Book> books) throws SQLException {
        books = bookRepo.getRepairBook();
        books.sort((b1, b2) -> {
            try {
                int severity1 = bookRepo.getSeverity(b1.getDamaged());
                int severity2 = bookRepo.getSeverity(b2.getDamaged());
                return severity2 - severity1; // Sort in descending order of severity
            } catch (SQLException e) {
                e.printStackTrace();
                return 0;
            }
        });
        return books;
    }
    

    // public List<Book> sortBooks(List<Book> books) throws SQLException {

    //     books = bookRepo.getRepairBook();
    //     books.sort((b1, b2) -> {
    //         return getSeverity(b2) - getSeverity(b1);
    //     });
    //     return books;
    // }

}
