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
}