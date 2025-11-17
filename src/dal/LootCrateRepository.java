package dal;

import config.DatabaseConfig;
import exceptions.LootCrateNotFoundException;
import models.LootCrate;

import java.sql.*;

public class LootCrateRepository {
    private final DatabaseConfig config;

    public LootCrateRepository(DatabaseConfig config) {
         this.config = config;
    }

    public LootCrate findById(int id) throws LootCrateNotFoundException {
        String sql = "SELECT id, price, name FROM lootcrates WHERE id = ?";

        try (Connection conn = DriverManager.getConnection(config.url, config.username, config.password);
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, id);

            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    int crateId = rs.getInt("id");
                    int price = rs.getInt("price");
                    String name = rs.getString("name");
                    return new LootCrate(crateId, price,name);
                } else {
                    throw new LootCrateNotFoundException("Crate '" + id + "' blev ikke fundet");
                }
            }

        } catch (SQLException e) {
            throw new RuntimeException("Database error occurred.");
        }

    }
    public void addLootCrate(LootCrate crate) {
        String sql = "INSERT INTO lootcrates (id, price, name) VALUES (?, ?, ?)";
        try (Connection conn = DriverManager.getConnection(config.url, config.username, config.password);  ) {
            PreparedStatement stmt = conn.prepareStatement(sql);

            stmt.setInt(1, crate.getCrateId());
            stmt.setInt(2, crate.getPrice());
            stmt.setString(3, crate.getName());
            stmt.executeUpdate();

        } catch (SQLException e) {
            throw new RuntimeException("Database error occurred.");
        }
    }
}

