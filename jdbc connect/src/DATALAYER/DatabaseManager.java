package DATALAYER;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DatabaseManager {
    private static Connection con;

    // Private constructor to prevent instantiation
    public DatabaseManager() {}

    public  Connection getConnection() {
        if (con == null) {
            try {
                Class.forName("com.mysql.cj.jdbc.Driver");
                con = DriverManager.getConnection("jdbc:mysql://localhost:3306/library", "root", "");
                System.out.println("Connected to the database!");
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
                System.err.println("Error: MySQL JDBC Driver not found!");
            } catch (SQLException e) {
                e.printStackTrace();
                System.err.println("Error: Failed to connect to the database!");
            }
        }
        return con;
    }
    // public static void closeConnection() {
    //     if (con != null) {
    //         try {
    //             con.close();
    //             System.out.println("Database connection closed.");
    //         } catch (SQLException e) {
    //             e.printStackTrace();
    //             System.err.println("Error: Failed to close database connection!");
    //         }
    //     }
    // }
}

