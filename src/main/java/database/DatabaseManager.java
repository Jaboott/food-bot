package database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DatabaseManager {

    private Connection connection;
    public DatabaseManager(String path) {
        try {
            connection = DriverManager.getConnection("jdbc:sqlite:" + path);
            System.out.println("SQLite Connection established");
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }


}
