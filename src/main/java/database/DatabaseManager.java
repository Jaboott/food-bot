package database;

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

    public void addRestaurant(String name) throws SQLException {
        String query = "INSERT INTO restaurants(name) VALUES (?)";
        try (var pstmt = connection.prepareStatement(query)) {
            pstmt.setString(1, name);
            pstmt.executeUpdate();
            System.out.printf("Restaurant: %s successfully added", name);
        } catch (SQLException e) {
            System.out.println("Failed to add restaurant");
            System.out.println(e.getMessage());
            throw e;
        }
    }

    public String getRestaurant() throws SQLException{
        String query = "SELECT name FROM restaurants WHERE id = (?)";
        try (var pstmt = connection.prepareStatement(query)){
            int nRow =  getRows();

            if (nRow == 0) {
                throw new SQLException("No restaurant in database");
            }

            int rRow = random.nextInt(nRow) + 1;
            pstmt.setInt(1, rRow);
            var result = pstmt.executeQuery();
            return result.getString("name");
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
