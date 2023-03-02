package org.television.connection;

import java.sql.*;

public class TPConnection {
    private static final String USER = "matter";
    private static final String PASSWORD = "0652";
    private static final String URL = "jdbc:sqlserver://localhost:1433;databaseName=Television;trustServerCertificate=true";
    public static Connection connect() {
        try {
            Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
            return DriverManager.getConnection(URL, USER, PASSWORD);
        } catch (Exception ex) {
            System.out.println("Error connecting to database: " + ex.getMessage());
        }
        return null;
    }
}
