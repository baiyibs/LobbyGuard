package me.baiyi.paper.lobbyguard;

public class FeatureManager {
    private static FeatureManager instance;
    private final ConfigManager configManager;

    private FeatureManager() {
        this.configManager = ConfigManager.getInstance();
    }

    public static FeatureManager getInstance() {
        if (instance == null) {
            instance = new FeatureManager();
        }
        return instance;
    }

    public boolean isCreatureSpawnEnabled() {
        return configManager.getConfig().getBoolean("features.creature-spawn", false);
    }

    public boolean isBlockBreakEnabled() {
        return configManager.getConfig().getBoolean("features.block-break", false);
    }

    public boolean isBlockPlaceEnabled() {
        return configManager.getConfig().getBoolean("features.block-place", false);
    }

    public boolean isPlayerDamageEnabled() {
        return configManager.getConfig().getBoolean("features.player-damage", false);
    }

    public boolean isHungerEnabled() {
        return configManager.getConfig().getBoolean("features.hunger", false);
    }

    public boolean isItemDropEnabled() {
        return configManager.getConfig().getBoolean("features.item-drop", false);
    }

    public boolean isItemPickupEnabled() {
        return configManager.getConfig().getBoolean("features.item-pickup", false);
    }

    public boolean isAdventureOnJoinEnabled() {
        return configManager.getConfig().getBoolean("features.adventure-on-join", true);
    }

    public boolean isClearInventoryOnJoinEnabled() {
        return configManager.getConfig().getBoolean("features.clear-inventory-on-join", true);
    }
}