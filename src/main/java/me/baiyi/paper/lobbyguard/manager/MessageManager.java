package me.baiyi.paper.lobbyguard.manager;

public class MessageManager {
    private static MessageManager instance;
    private ConfigManager configManager;

    private MessageManager() {
        this.configManager = ConfigManager.getInstance();
    }

    public static MessageManager getInstance() {
        if (instance == null) {
            instance = new MessageManager();
        }
        return instance;
    }

    public void reloadMessages() {
        this.configManager = ConfigManager.getInstance();
    }

    public String getMessage(String path, String defaultValue) {
        return configManager.getConfig().getString(path, defaultValue);
    }

    public String getMessage(String path) {
        return configManager.getConfig().getString(path);
    }
}