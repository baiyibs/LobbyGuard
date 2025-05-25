package me.baiyi.paper.lobbyguard.manager;

import me.baiyi.paper.lobbyguard.LobbyGuard;
import org.bukkit.configuration.file.FileConfiguration;

public class ConfigManager {
    private static ConfigManager instance;
    private FileConfiguration config;

    private ConfigManager() {
        LobbyGuard.getInstance().saveDefaultConfig();
        config = LobbyGuard.getInstance().getConfig();
    }

    public static ConfigManager getInstance() {
        if (instance == null) {
            instance = new ConfigManager();
        }
        return instance;
    }

    public void reloadConfig() {
        LobbyGuard.getInstance().reloadConfig();
        config = LobbyGuard.getInstance().getConfig();
    }

    public FileConfiguration getConfig() {
        return config;
    }
}