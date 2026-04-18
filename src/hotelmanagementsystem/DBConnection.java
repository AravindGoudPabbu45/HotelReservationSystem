package hotelmanagementsystem;
import java.sql.Connection;
import java.sql.DriverManager;
public class DBConnection {
    static Connection con = null;
    public static Connection getConnection() {
        try {
            if (con == null || con.isClosed()) {
                Class.forName("oracle.jdbc.driver.OracleDriver");
                con = DriverManager.getConnection(
                    "jdbc:oracle:thin:@localhost:1521/XEPDB1",
                    "java_project",
                    "aravind45"
                );
                System.out.println("Database connected successfully");
            }
        } catch (Exception e) {
            System.out.println("Connection failed: " + e.getMessage());
        }
        return con;
    }
    public static void closeConnection() {
        try {
            if (con != null && !con.isClosed()) {
                con.close();
                System.out.println("Connection closed");
            }
        } catch (Exception e) {
            System.out.println("Error closing: " + e.getMessage());
        }
    }
}
