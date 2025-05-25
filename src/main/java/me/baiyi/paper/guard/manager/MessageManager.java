package me.baiyi.paper.guard.manager;

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
        String message = configManager.getConfig().getString(path, defaultValue);
        return formatMessage(message);
    }

    public String getMessage(String path) {
        String message = configManager.getConfig().getString(path);
        return formatMessage(message);
    }

    private String formatMessage(String message) {
        if (message == null) return "";
        String prefix = configManager.getConfig().getString("message-prefix", "§8[§bGuard§8] §7");
        return message.replace("%prefix%", prefix);
    }
}