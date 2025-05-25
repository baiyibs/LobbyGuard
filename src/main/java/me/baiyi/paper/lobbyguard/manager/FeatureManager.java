package me.baiyi.paper.lobbyguard.manager;

public class FeatureManager {
    private static FeatureManager instance;
    private ConfigManager configManager;

    private FeatureManager() {
        this.configManager = ConfigManager.getInstance();
    }

    public static FeatureManager getInstance() {
        if (instance == null) {
            instance = new FeatureManager();
        }
        return instance;
    }

    public void reloadFeatures() {
        this.configManager = ConfigManager.getInstance();
    }

    public boolean isCreatureSpawnEnabled() {
        return !configManager.getConfig().getBoolean("creature-spawn", true);
    }

    public boolean isBlockBreakEnabled() {
        return !configManager.getConfig().getBoolean("block-break", true);
    }

    public boolean isBlockPlaceEnabled() {
        return !configManager.getConfig().getBoolean("block-place", true);
    }

    public boolean isPlayerDamageEnabled() {
        return !configManager.getConfig().getBoolean("player-damage", true);
    }

    public boolean isHungerEnabled() {
        return !configManager.getConfig().getBoolean("hunger", true);
    }

    public boolean isItemDropEnabled() {
        return !configManager.getConfig().getBoolean("item-drop", true);
    }

    public boolean isItemPickupEnabled() {
        return !configManager.getConfig().getBoolean("item-pickup", true);
    }

    public boolean isAdventureOnJoinEnabled() {
        return configManager.getConfig().getBoolean("adventure-on-join", true);
    }

    public boolean isClearInventoryOnJoinEnabled() {
        return configManager.getConfig().getBoolean("clear-inventory-on-join", true);
    }
}