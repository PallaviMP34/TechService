package BUSINESSLAYER;

import java.sql.SQLException;
import java.util.List;

import ENTITY.Book;
import ENTITY.Dummy;
 

 
public interface BookService {
     public  List<Book> getAllBooks() throws SQLException ;
     public Book getBookById(int bookId) throws SQLException;
     public boolean createBook(Dummy book) throws SQLException;
     public int updateBook(int bookId,String title,int author_id,int genre_id,String publication_date,String damaged) throws SQLException;
     public int deleteBook(int bookId) throws SQLException;
}
