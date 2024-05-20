import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class dbscn {
    private static Connection connection;

    public static Connection getConnection() {
        if (connection == null) {
            String URL = "jdbc:mysql://localhost:3306/susan";
            String USERNAME = "root";
            String PASSWORD = "Zerominde1.";
            try {
                Class.forName("com.mysql.cj.jdbc.Driver");
                connection = DriverManager.getConnection(URL, USERNAME, PASSWORD);
                System.out.println();
            } catch (ClassNotFoundException e) {
                System.out.println("MySQL JDBC Driver not found");
                e.printStackTrace();
            } catch (SQLException e) {
                System.out.println("Connection failed");
                e.printStackTrace();
            }
        }
        return connection;
    }
}
