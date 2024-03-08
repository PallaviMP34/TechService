package DATALAYER;
import java.sql.Connection;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import ENTITY.Book;
import ENTITY.Dummy;


public class BookRepository {
   DatabaseManager dbManager = new DatabaseManager();

    public List<Book> getAllBooks() throws SQLException {
        List<Book> books = new ArrayList<>();
        String query = "SELECT * FROM books";
        Connection con=null;
        try {
            con=dbManager.getConnection();
            PreparedStatement statement = con.prepareStatement(query);
             ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                int id = resultSet.getInt("book_id");
                String title = resultSet.getString("title");
                int authorId = resultSet.getInt("author_id");
                int genreId = resultSet.getInt("genre_id");
                String publicationDate = resultSet.getString("publication_date");
                String damaged=resultSet.getString("damaged");
                String repairStatus=resultSet.getString("repair_status");
                Book book = new Book(id, title, authorId, genreId, publicationDate,damaged,repairStatus);
                books.add(book);
            }
        }
        catch(SQLException e){

        }
        return books;
    }

    public Book getBookById(int bookId) throws SQLException {
        String query = "SELECT * FROM books WHERE book_id = ?";
        Book book = null;
        Connection con=null;

        try {
            con=dbManager.getConnection();
            PreparedStatement statement = con.prepareStatement(query) ;
            statement.setInt(1, bookId);
            try (ResultSet resultSet = statement.executeQuery()) {
                if (resultSet.next()) {
                    int id = resultSet.getInt("book_id");
                    String title = resultSet.getString("title");
                    int authorId = resultSet.getInt("author_id");
                    int genreId = resultSet.getInt("genre_id");
                    String publicationDate = resultSet.getString("publication_date");
                    String damaged=resultSet.getString("damaged");
                    String repairStatus=resultSet.getString("repair_status");
                    book= new Book(id, title, authorId, genreId, publicationDate,damaged,repairStatus);
                
            }
        }
        }catch(SQLException e){

            e.printStackTrace();
        }
        
        return book;
    
    }

    public boolean createBook(Dummy book) throws SQLException {
        String query = "INSERT INTO books (title, author_id, genre_id, publication_date, damaged,repair_status) VALUES (?, ?, ?, ?, ?, ?)";
        Connection con = null;
        try {
            con = dbManager.getConnection();
            PreparedStatement statement = con.prepareStatement(query);
            statement.setString(1, book.getTitle());
            statement.setInt(2, book.getAuthorId());
            statement.setInt(3, book.getGenreId());
            statement.setString(4, book.getPublicationDate());
            statement.setString(5, book.getDamaged()); 
            statement.setString(6, book.getRepairStatus());
           
            int rowsInserted = statement.executeUpdate();
            return rowsInserted > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        } 
    }
    
    public int updateBook(int bookId,String title,int author_id,int genre_id,String publication_date,String damaged) throws SQLException {

       
        String query = "UPDATE books SET title=?, author_id=?, genre_id=?, publication_date=?, damaged=? WHERE book_id=?";
        int rowsUpdated=0;
        Connection con=null;
        try {
            con=dbManager.getConnection();
            PreparedStatement statement = con.prepareStatement(query);
            statement.setString(1, title);
            statement.setInt(2,author_id);
            statement.setInt(3,genre_id);
            statement.setString(4, publication_date);
            statement.setString(5, damaged);
            statement.setInt(6, bookId);
             rowsUpdated = statement.executeUpdate();
           
        }
        catch(SQLException e){
            e.printStackTrace();

        }
        return rowsUpdated;
    }

    public int deleteBook(int bookId) throws SQLException {
        String query = "DELETE FROM books WHERE book_id=?";
        int rowsDeleted =0;
        Connection con=null;
        try {
            con=dbManager.getConnection();
            PreparedStatement statement = con.prepareStatement(query);
            statement.setInt(1, bookId);
            rowsDeleted = statement.executeUpdate();
           
        }catch(SQLException e){
            e.printStackTrace();
        }
        return rowsDeleted ;
    }

    public void updateDataStatus(int bookId, String repair_status) throws SQLException {
        Connection connection = null;
        try {
            connection=dbManager.getConnection();
 
            String q1 = "update books set repair_status=? where book_id=?";
            PreparedStatement stmnt1 = connection.prepareStatement(q1);
            stmnt1.setString(1, repair_status);
            stmnt1.setInt(2, bookId);
            stmnt1.executeUpdate();
        }
      catch(SQLException e){

      }
    }
 
    public List<Book> getRepairBook() throws SQLException{
        Connection connection = null;
       List<Book> books = new ArrayList<>();
        try {
            connection = dbManager.getConnection();
           
            String query = "select * from books where damaged !=? ";
           
            PreparedStatement stmt = connection.prepareStatement(query);
            stmt.setString(1,"no damage");
       
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                int bookId = rs.getInt("book_id");
                String bookName = rs.getString("title");
                String publishDate = rs.getString("publication_date");
                int authorId = rs.getInt("author_id");
                int genId = rs.getInt("genre_id");
                String damage=rs.getString("damaged");
                String repairStatus=rs.getString("repair_status");
                books.add(new Book(bookId, bookName, authorId, genId, publishDate,damage,repairStatus));
            }
            
 
        } catch(SQLException e){
           
        }
       return books;
    }
   
    public boolean authorExists(int authorId) {

        try{
        String query = "SELECT author_id FROM authors WHERE author_id = ?";
       Connection con = dbManager.getConnection();
             PreparedStatement statement = con.prepareStatement(query);
            statement.setInt(1, authorId);
            ResultSet resultSet = statement.executeQuery();
            return resultSet.next();
               
        
    }catch(SQLException e){
        e.printStackTrace();
    }
        return false; 
    }
    
    public boolean genreExists(int genreId) {
        try{
        String query = "SELECT genre_id FROM genres WHERE genre_id = ?";
        Connection con = dbManager.getConnection();
             PreparedStatement statement = con.prepareStatement(query);
            statement.setInt(1, genreId);
            ResultSet resultSet = statement.executeQuery();
              return resultSet.next();
            }
        
    catch(SQLException e){
        e.printStackTrace();
    }
        return false; 
    }
    public boolean bookExists(int Id) {
        try{
        String query = "SELECT book_id FROM genres WHERE book_id = ?";
        Connection con = dbManager.getConnection();
             PreparedStatement statement = con.prepareStatement(query);
            statement.setInt(1,Id);
            ResultSet resultSet = statement.executeQuery();
              return resultSet.next();
            }
        
    catch(SQLException e){
        e.printStackTrace();
    }
        return false; 
    }

   
    public int getSeverity(String damageType) throws SQLException {
        Connection connection = null;
        
        try {
            connection = dbManager.getConnection();
            String query = "SELECT severity_level FROM Damages WHERE damage_type = ?";
             PreparedStatement statement = connection.prepareStatement(query) ;
            statement.setString(1, damageType);
            try (ResultSet resultSet = statement.executeQuery()) {
                if (resultSet.next()) {
                    return resultSet.getInt("severity_level");
                }
            }
        }catch(SQLException e){

        }
        return 0; // Default severity level if not found
    }

    public int setPrice(String damageType) throws SQLException {
        Connection connection = null;
        
        try {
             connection = dbManager.getConnection();
             String query = "SELECT repair_cost FROM Damages WHERE damage_type = ?";
             PreparedStatement statement = connection.prepareStatement(query);
            statement.setString(1, damageType);
            try (ResultSet resultSet = statement.executeQuery()) {
                if (resultSet.next()) {
                    return resultSet.getInt("repair_cost");
                }
            }
        }catch(SQLException e){

        }
        return 0; // Default repair cost if not found
    }



}