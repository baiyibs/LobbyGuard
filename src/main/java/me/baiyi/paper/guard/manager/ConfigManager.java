package me.baiyi.paper.guard.manager;

import me.baiyi.paper.guard.Guard;
import org.bukkit.configuration.file.FileConfiguration;

public class ConfigManager {
    private static ConfigManager instance;
    private FileConfiguration config;

    private ConfigManager() {
        Guard.getInstance().saveDefaultConfig();
        config = Guard.getInstance().getConfig();
    }

    public static ConfigManager getInstance() {
        if (instance == null) {
            instance = new ConfigManager();
        }
        return instance;
    }

    public void reloadConfig() {
        Guard.getInstance().reloadConfig();
        config = Guard.getInstance().getConfig();
    }

    public FileConfiguration getConfig() {
        return config;
    }

    public String getDatabaseUrl() {
        String host = config.getString("database.host", "localhost");
        int port = config.getInt("database.port", 3306);
        String database = config.getString("database.database", "guard");
        return "jdbc:mysql://" + host + ":" + port + "/" + database + "?createDatabaseIfNotExist=true";
    }

    public String getDatabaseUsername() {
        return config.getString("database.username", "root");
    }

    public String getDatabasePassword() {
        return config.getString("database.password", "password");
    }

    public String getDatabaseTable() {
        return config.getString("database.table", "whitelist");
    }
}