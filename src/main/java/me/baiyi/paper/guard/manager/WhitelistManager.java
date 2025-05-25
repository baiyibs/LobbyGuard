package me.baiyi.paper.guard.manager;

import me.baiyi.paper.guard.Guard;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class WhitelistManager {
    private static WhitelistManager instance;
    private ConfigManager configManager;
    private List<String> whitelistPlayers;
    private boolean databaseConnectedSuccessfully = false;

    private WhitelistManager() {
        this.configManager = ConfigManager.getInstance();
        String source = configManager.getConfig().getString("whitelist.source", "config");
        if ("database".equalsIgnoreCase(source)) {
            testDatabaseConnection();
            // If database connection failed, switch source to config
            if (!databaseConnectedSuccessfully) {
                configManager.getConfig().set("whitelist.source", "config");
                Guard.getInstance().getLogger().warning("Database connection failed, switching whitelist source to 'config'.");
            }
        }
        loadWhitelist();
    }

    public static WhitelistManager getInstance() {
        if (instance == null) {
            instance = new WhitelistManager();
        }
        return instance;
    }

    public void reloadWhitelist() {
        this.configManager = ConfigManager.getInstance();
        loadWhitelist();
    }

    public boolean isWhitelistEnabled() {
        return configManager.getConfig().getBoolean("whitelist.enabled", false);
    }

    public List<String> getWhitelistPlayers() {
        return whitelistPlayers;
    }

    private void testDatabaseConnection() {
        String url = configManager.getDatabaseUrl();
        String user = configManager.getDatabaseUsername();
        String password = configManager.getDatabasePassword();

        try (Connection conn = DriverManager.getConnection(url, user, password)) {
            Guard.getInstance().getLogger().info("Successfully connected to the database.");
            databaseConnectedSuccessfully = true;
        } catch (SQLException e) {
            Guard.getInstance().getLogger().severe("Failed to connect to the database: " + e.getMessage());
            databaseConnectedSuccessfully = false;
        }
    }

    private void loadWhitelist() {
        whitelistPlayers = new ArrayList<>();
        if (!isWhitelistEnabled()) {
            return;
        }

        String source = configManager.getConfig().getString("whitelist.source", "config");

        if ("database".equalsIgnoreCase(source)) {
            loadWhitelistFromDatabase();
        } else {
            loadWhitelistFromConfig();
        }
    }

    private void loadWhitelistFromConfig() {
        whitelistPlayers = configManager.getConfig().getStringList("whitelist.players");
    }

    private void loadWhitelistFromDatabase() {
        whitelistPlayers = new ArrayList<>();
        if (!isWhitelistEnabled()) {
            return;
        }

        String url = configManager.getDatabaseUrl();
        String user = configManager.getDatabaseUsername();
        String password = configManager.getDatabasePassword();
        String table = configManager.getDatabaseTable();

        try (Connection conn = DriverManager.getConnection(url, user, password)) {
            // 检查表是否存在，如果不存在则创建
            if (!tableExists(conn, table)) {
                createWhitelistTable(conn, table);
            }
            String sql = "SELECT player_name FROM " + table;
            try (PreparedStatement stmt = conn.prepareStatement(sql)) {
                try (ResultSet rs = stmt.executeQuery()) {
                    while (rs.next()) {
                        whitelistPlayers.add(rs.getString("player_name"));
                    }
                }
            }
        } catch (SQLException e) {
            Guard.getInstance().getLogger().severe("Failed to load whitelist from database: " + e.getMessage());
        }
    }

    private boolean tableExists(Connection conn, String tableName) throws SQLException {
        try (ResultSet rs = conn.getMetaData().getTables(null, null, tableName, null)) {
            return rs.next();
        }
    }

    private void createWhitelistTable(Connection conn, String tableName) throws SQLException {
        String sql = "CREATE TABLE " + tableName + " (player_name VARCHAR(255) NOT NULL UNIQUE)";
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.execute();
            Guard.getInstance().getLogger().info("Created whitelist table: " + tableName);
        }
    }
}