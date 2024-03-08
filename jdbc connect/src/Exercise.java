import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import DATALAYER.DatabaseManager;

public class Exercise {
    DatabaseManager Db=new  DatabaseManager();

    private Connection con =Db.getConnection();
public void selectBook() {
    String query = "SELECT * FROM Books WHERE title LIKE '%s'";
    try (Statement statement = con.createStatement();
         ResultSet resultSet = statement.executeQuery(query)) {
        while (resultSet.next()) {
            int id = resultSet.getInt("book_id");
            String title = resultSet.getString("title");
            int authorId = resultSet.getInt("author_id");
            int genre_id = resultSet.getInt("genre_id");
            String publication_date = resultSet.getString("publication_date");
            System.out.println("id: " + id + ", Title: " + title + ", genre_id: " + genre_id + ", Author ID: " + authorId + ", Publication_date: " + publication_date);
        }
    } catch (SQLException e) {
        e.printStackTrace();
    }
}
public void select1() {
    String query = "SELECT * FROM books WHERE author_id IN (6, 8, 7);";
    try (Statement statement = con.createStatement();
         ResultSet resultSet = statement.executeQuery(query)) {
        while (resultSet.next()) {
            int id = resultSet.getInt("book_id");
            String title = resultSet.getString("title");
            int authorId = resultSet.getInt("author_id");
            int genre_id = resultSet.getInt("genre_id");
            String publication_date = resultSet.getString("publication_date");
            System.out.println("id: " + id + ", Title: " + title + ", genre_id: " + genre_id + ", Author ID: " + authorId + ", Publication_date: " + publication_date);
        }
    } catch (SQLException e) {
        e.printStackTrace();
    }
}
public void select2() {
    String query = "SELECT title, publication_date FROM Books WHERE publication_date > '1980-01-01'";
    try (Statement statement = con.createStatement();
         ResultSet resultSet = statement.executeQuery(query)) {
        while (resultSet.next()) {
            String title = resultSet.getString("title");
            String publication_date = resultSet.getString("publication_date");
            System.out.println("Title: " + title + ", Publication Date: " + publication_date);
        }
    } catch (SQLException e) {
        e.printStackTrace();
    }
}
public void selectOrder() {
    String query = "SELECT * FROM books ORDER BY genre_id ASC, publication_date DESC;";
    try (Statement statement = con.createStatement();
         ResultSet resultSet = statement.executeQuery(query)) {
        while (resultSet.next()) {
            String title = resultSet.getString("title");
            String publication_date = resultSet.getString("publication_date");
            System.out.println("Title: " + title + ", Publication Date: " + publication_date);
        }
    } catch (SQLException e) {
        e.printStackTrace();
    }
}

public void selectGroup() {
    String query = "SELECT genre_id, COUNT(*) AS count FROM books GROUP BY genre_id;";
    try (Statement statement = con.createStatement();
         ResultSet resultSet = statement.executeQuery(query)) {
        while (resultSet.next()) {
            int genre_id = resultSet.getInt("genre_id");
            int count = resultSet.getInt("count");
            System.out.println("Genre ID: " + genre_id + ", Count: " + count);
        }
    } catch (SQLException e) {
        e.printStackTrace();
    }
}

public void selectHaving() {
    String query = "SELECT author_id, AVG(YEAR(publication_date)) AS avg_publication_year FROM books GROUP BY author_id HAVING AVG(YEAR(publication_date)) > 2000;";
    try (Statement statement = con.createStatement();
         ResultSet resultSet = statement.executeQuery(query)) {
        while (resultSet.next()) {
            int author_id = resultSet.getInt("author_id");
            double avg_publication_year = resultSet.getDouble("avg_publication_year");
            System.out.println("Author ID: " + author_id + ", Average Publication Year: " + avg_publication_year);
        }
    } catch (SQLException e) {
        e.printStackTrace();
    }
}

public void selectjoin() {
    String query = "SELECT title, genre_name, author_name FROM Books B JOIN Authors A ON B.author_id=A.author_id JOIN Genres G ON B.genre_id=G.genre_id;";
    try (Statement statement = con.createStatement();
         ResultSet resultSet = statement.executeQuery(query)) {
        while (resultSet.next()) {
            String title = resultSet.getString("title");
            String genre_name = resultSet.getString("genre_name");
            String author_name = resultSet.getString("author_name");
            System.out.println("Title: " + title + ", Genre: " + genre_name + ", Author: " + author_name);
        }
    } catch (SQLException e) {
        e.printStackTrace();
    }
}

public static void main(String[] args) throws Exception {

    Exercise exercise = new Exercise();
  
    exercise.selectBook();
    exercise.select1();
    exercise.select2();
    exercise.selectOrder();
    exercise.selectGroup();
    exercise.selectHaving();
    exercise.selectjoin();
    DatabaseManager.closeConnection();
}
}

