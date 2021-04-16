import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DB {
    private DB(){};
    public static Connection  connect() {
        Connection conn = null;
        try {
            // db parameters
            String url = "jdbc:sqlite:DB\\DB.db";
            // create a connection to the database
            conn = DriverManager.getConnection(url);
            System.out.println("Connection to SQLite has been established.");
            return conn;
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }
}