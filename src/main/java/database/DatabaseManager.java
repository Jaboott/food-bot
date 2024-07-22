package database;

import restaurant.Cuisine;
import restaurant.GeneralLocation;
import restaurant.Restaurant;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Random;

public class DatabaseManager {

    private Connection connection;
    private Random random;

    public DatabaseManager() {
        this("data/restaurants.db");
    }

    public DatabaseManager(String path) {
        try {
            connection = DriverManager.getConnection("jdbc:sqlite:" + path);
            random = new Random();
            System.out.println("SQLite Connection established");
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    public void addRestaurant(Restaurant restaurant) throws SQLException {
        String query = "INSERT INTO restaurants(name, normalized_name, cuisine, location) VALUES (?, ?, ?, ?)";
        try (var pstmt = connection.prepareStatement(query)) {
            pstmt.setString(1, restaurant.getName());
            pstmt.setString(2, restaurant.getNormalizedName());
            pstmt.setString(3, restaurant.getType());
            pstmt.setString(4, restaurant.getGeneralLocation());

            pstmt.executeUpdate();
            System.out.printf("Restaurant: %s successfully added", restaurant.getName());
        } catch (SQLException e) {
            //TODO add uniqueness check
            System.out.println("Failed to add restaurant");
            System.out.println(e.getMessage());
            throw e;
        }
    }

    //TODO
    public void deleteRestaurant(String name) {

    }

    public Restaurant getRestaurant() throws SQLException{
        String query = "SELECT * FROM restaurants ORDER BY RANDOM() LIMIT 1";
        try (var pstmt = connection.prepareStatement(query)){
            var result = pstmt.executeQuery();
            Restaurant restaurant = new Restaurant();

            restaurant.setName(result.getString("name"));
            restaurant.setType(Cuisine.valueOf(result.getString("cuisine")));
            restaurant.setGeneralLocation(GeneralLocation.valueOf(result.getString("location")));
            return restaurant;
        } catch (SQLException e) {
            System.out.println("Failed to get restaurant");
            throw e;
        }
    }

    private int getRows() throws SQLException {
        String query = "SELECT COUNT(name) FROM restaurants";
        try (var stmt = connection.createStatement()) {
            var rows = stmt.executeQuery(query);
            return rows.getInt("count(name)");
        } catch (SQLException e) {
            System.out.println("Failed to get rows.");
            throw e;
        }
    }

}
