package DATALAYER;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DatabaseManager {
   

    public  Connection getConnection() {
        ResourceBundle rd
        = ResourceBundle.getBundle("resource.system", Locale.US);

    String jdbcUrl = rd.getString("url");
    String username = rd.getString("userName");
    String password = rd.getString("");
    String driverClass=rd.getString("driver");
    Connection connection = null;
    try {
        // Step 1: Register JDBC driver
        Class.forName(driverClass);
        // Step 2: Open a connection
        System.out.println("Connecting to database...");
        connection = DriverManager.getConnection(jdbcUrl, username, password);
    }
    catch(Exception e ){
        e.printStackTrace();
    }
       return connection;
    }
}

