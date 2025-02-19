package database;

import restaurant.Cuisine;
import restaurant.GeneralLocation;
import restaurant.Restaurant;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

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

    public void addRestaurant(Restaurant restaurant) throws SQLException {
        String queryMain = "INSERT INTO restaurants(name, normalized_name, cuisine, location) VALUES (?, ?, ?, ?)";
        String queryFts = "INSERT INTO restaurants_fts(rowid, name, cuisine, location) VALUES (?, ?, ?, ?)";
        int id;

        // Attempt adding to main table
        try {
            // Connection won't commit until both update is done successfully
            connection.setAutoCommit(false);

            try (var pstmt = connection.prepareStatement(queryMain, Statement.RETURN_GENERATED_KEYS)) {
                pstmt.setString(1, restaurant.getName());
                pstmt.setString(2, restaurant.getNormalizedName());
                pstmt.setString(3, restaurant.getType());
                pstmt.setString(4, restaurant.getGeneralLocation());
                pstmt.executeUpdate();

                // Get the id generated by AUTOINCREMENT
                try (ResultSet generatedKey = pstmt.getGeneratedKeys()) {
                    if (generatedKey.next()) {
                        id = generatedKey.getInt(1);
                    } else {
                        throw new SQLException("Failed to retrieve id");
                    }
                }
            }

            // Attempt adding to fts table
            try (var pstmt = connection.prepareStatement(queryFts)) {
                pstmt.setInt(1, id);
                pstmt.setString(2, restaurant.getName());
                pstmt.setString(3, restaurant.getType());
                pstmt.setString(4, restaurant.getGeneralLocation());
                pstmt.executeUpdate();
            }

            connection.commit();
        } catch (SQLException e) {
            System.out.println("Failed to add restaurant");
            System.out.println(e.getMessage());
            connection.rollback();
            throw e;
        } finally {
            connection.setAutoCommit(true);
        }

    }

    public void deleteRestaurant(String name) throws SQLException {
        String queryMain = "DELETE FROM restaurants WHERE name = ?";
        String queryFts = "DELETE FROM restaurants_fts WHERE name = ?";

        // Using transaction to delete from both tables
        try  {
            connection.setAutoCommit(false);
            try (var pstmt = connection.prepareStatement(queryMain)) {
                pstmt.setString(1, name);
                pstmt.executeUpdate();
            }

            try (var pstmt = connection.prepareStatement(queryFts)) {
                pstmt.setString(1, name);
                pstmt.executeUpdate();
            }
            connection.commit();
            System.out.println(this.getClass() + ": Deleted restaurant from database");
        } catch (SQLException e) {
            System.out.println(this.getClass() + "Failed to delete restaurant");
            System.out.println(e.getMessage());
            throw e;
        } finally {
            connection.setAutoCommit(true);
        }
    }

    public Restaurant getRandomRestaurant(String cuisine, String location) throws SQLException {
        StringBuilder query = new StringBuilder("SELECT * FROM restaurants WHERE 1=1");
        if (cuisine != null) {
            query.append(" AND cuisine = ?");
        }
        if (location != null) {
            query.append(" AND location = ?");
        }
        query.append(" ORDER BY RANDOM() LIMIT 1");

        try (var pstmt = connection.prepareStatement(query.toString())){
            int index = 1;

            if (cuisine != null) {
                pstmt.setString(index++, cuisine);
            }
            if (location != null) {
                pstmt.setString(index, location);
            }

            var result = pstmt.executeQuery();

            // return null if no result matches specified settings
            if (!result.next()) {
                return null;
            }
            Restaurant restaurant = new Restaurant();

            restaurant.setName(result.getString("name"));
            restaurant.setType(Cuisine.valueOf(result.getString("cuisine")));
            restaurant.setGeneralLocation(GeneralLocation.valueOf(result.getString("location")));
            return restaurant;
        } catch (SQLException e) {
            System.out.println(this.getClass() + "Failed to get restaurant");
            System.out.println(e.getMessage());
            throw e;
        }
    }

    public List<Restaurant> searchRestaurants(String name) throws SQLException {
        String query = "SELECT * FROM restaurants_fts WHERE name MATCH ?";
        System.out.printf("Searching for name: %s%n", name);

        try (var pstmt = connection.prepareStatement(query)) {
            pstmt.setString(1, name);
            var rs = pstmt.executeQuery();

            return buildRestaurants(rs);
        } catch (SQLException e) {
            System.out.println(e.getMessage());
            throw e;
        }
    }

    public List<Restaurant> showAll() throws SQLException {
        String query = "SELECT * FROM restaurants ORDER BY name";

        try (var pstmt = connection.prepareStatement(query)) {
            var rs = pstmt.executeQuery();

            return buildRestaurants(rs);
        } catch (SQLException e) {
            System.out.println(e.getMessage());
            throw e;
        }
    }

    private List<Restaurant> buildRestaurants(ResultSet rs) throws SQLException {
        List<Restaurant> restaurants = new ArrayList<>();

        // Building the list of restaurants from database query
        while(rs.next()) {
            String restaurant = rs.getString("name");
            Cuisine cuisine = Cuisine.valueOf(rs.getString("cuisine"));
            GeneralLocation generalLocation = GeneralLocation.valueOf(rs.getString("location"));
            restaurants.add(new Restaurant(restaurant, cuisine, generalLocation));
        }

        return restaurants;
    }
}
