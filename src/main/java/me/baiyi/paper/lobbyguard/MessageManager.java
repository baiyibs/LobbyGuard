package me.baiyi.paper.lobbyguard;

public class MessageManager {
    private static MessageManager instance;
    private final ConfigManager configManager;

    private MessageManager() {
        this.configManager = ConfigManager.getInstance();
    }

    public static MessageManager getInstance() {
        if (instance == null) {
            instance = new MessageManager();
        }
        return instance;
    }

    public String getMessage(String path) {
        return configManager.getConfig().getString(path);
    }

    public String getMessage(String path, String defaultValue) {
        return configManager.getConfig().getString(path, defaultValue);
    }
}