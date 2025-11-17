package dal;

import config.DatabaseConfig;
import exceptions.PlayerNotFoundException;
import models.Player;

import java.sql.*;

public class PlayerRepository {
    private final DatabaseConfig config;

    public PlayerRepository(DatabaseConfig config) {
        this.config = config;
    }

    public Player findByPlayerName(String playerName) throws PlayerNotFoundException {
        String sql = "SELECT username, credits FROM players WHERE username = ?";

        try (Connection conn = DriverManager.getConnection(config.url, config.username, config.password)) {
            PreparedStatement stmt = conn.prepareStatement(sql);

            stmt.setString(1, playerName);

            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    String name = rs.getString("username");
                    int credits = rs.getInt("credits");
                    return new Player(name, credits);
                } else {
                    throw new PlayerNotFoundException("Player '" + playerName + "' not found.");
                }
            }

        } catch (SQLException e) {
            System.out.println(e.getMessage());
            throw new RuntimeException("Database error occurred.");
        }
    }

    public void addPlayer(Player player) {
        String sql = "INSERT INTO players (username, credits) VALUES (?, ?)";

        try (Connection conn = DriverManager.getConnection(config.url, config.username, config.password)) {
            PreparedStatement stmt = conn.prepareStatement(sql);

            stmt.setString(1, player.getPlayerName());
            stmt.setInt(2, player.getCredits());
            stmt.executeUpdate();

        } catch (SQLException e) {
            throw new RuntimeException("Database error occurred.");
        }
    }

    public void updatePlayerCredits(String playerName, int newCredits) {
        String sql = "UPDATE players SET credits = ? WHERE username = ?";

        try (Connection conn = DriverManager.getConnection(config.url, config.username, config.password)) {
            PreparedStatement stmt = conn.prepareStatement(sql);

            stmt.setInt(1, newCredits);
            stmt.setString(2, playerName);
            stmt.executeUpdate();

        } catch (SQLException e) {
            throw new RuntimeException("Database error occurred.");
        }
    }
}
