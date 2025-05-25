package me.baiyi.paper.lobbyguard;

import org.bukkit.configuration.file.FileConfiguration;

public class ConfigManager {
    private static ConfigManager instance;
    private final LobbyGuard plugin;
    private FileConfiguration config;

    private ConfigManager(LobbyGuard plugin) {
        this.plugin = plugin;
        loadConfig();
    }

    public static ConfigManager getInstance() {
        if (instance == null) {
            instance = new ConfigManager(LobbyGuard.getInstance());
        }
        return instance;
    }

    private void loadConfig() {
        plugin.saveDefaultConfig();
        config = plugin.getConfig();
    }

    public void reloadConfig() {
        plugin.reloadConfig();
        config = plugin.getConfig();
    }

    public FileConfiguration getConfig() {
        return config;
    }
}