import java.sql.*;

public class TPConnection {
    public static Connection connect() {
        try {
            Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
            String url = "jdbc:sqlserver://localhost:1433;databaseName=Television;trustServerCertificate=true";

            String user = "matter";
            String password = "0652";

            return DriverManager.getConnection(url, user, password);
        } catch (Exception ex) {
            System.out.println("Error connecting to database: " + ex.getMessage());
        }
        return null;
    }
}
