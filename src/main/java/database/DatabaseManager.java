package database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DatabaseManager {

    private Connection connection;

    public DatabaseManager() {
        this("data/restaurants.db");
    }

    public DatabaseManager(String path) {
        try {
            connection = DriverManager.getConnection("jdbc:sqlite:" + path);
            System.out.println("SQLite Connection established");
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    public void addRestaurant(String name) throws SQLException {
        String query = "INSERT INTO restaurants(name) VALUES (?)";
        try (var pstmt = connection.prepareStatement(query)) {
            pstmt.setString(1, name);
            pstmt.executeUpdate();
            System.out.printf("Restaurant: %s successfully added", name);
        } catch (SQLException e) {
            System.out.println("Failed to add Restaurant");
            System.out.println(e.getMessage());
            throw e;
        }


    }

}
