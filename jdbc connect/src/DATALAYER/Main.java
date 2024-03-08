package DATALAYER;

import java.sql.SQLException;

import BUSINESSLAYER.BookServiceImplementation;

public class Main {
    public static void main(String[] args) throws SQLException {
        BookRepository bookRepository = new BookRepository();
        BookServiceImplementation bs =new BookServiceImplementation();

        // try {
            // Assuming bookId 1 exists in the database
        //     int bookId = 50;
        //     String newTitle = "Title";
        //     int newAuthorId = 2;
        //     int newGenreId = 3;
        //     String newPublicationDate = "2022-02-28"; // Assuming a valid date

        //     int rowsUpdated = bookRepository.updateBook(bookId, newTitle, newAuthorId, newGenreId, newPublicationDate);
            
        //     if (rowsUpdated > 0) {
        //         System.out.println("Book updated successfully.");
        //     } else {
        //         System.out.println("Failed to update book.");
        //     }
        // } catch (SQLException e) {
        //     e.printStackTrace();
        // }
        //System.out.println(bookRepository.getRepairBook());
       // System.out.println(bookRepository.getSeverity("missing pages"));
      // System.out.println(bookRepository.setPrice("missing pages"));
        System.out.println(bs.manageRepair());
           // System.out.println(bookRepository.getRepairBook());
    }
   
}
