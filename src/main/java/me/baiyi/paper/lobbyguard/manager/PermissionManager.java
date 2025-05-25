package me.baiyi.paper.lobbyguard.manager;

import org.bukkit.entity.Player;

public class PermissionManager {
    private static PermissionManager instance;

    private PermissionManager() {}

    public static PermissionManager getInstance() {
        if (instance == null) {
            instance = new PermissionManager();
        }
        return instance;
    }

    public boolean hasPermission(Player player, String permission) {
        if (player.hasPermission(permission)) {
            return true;
        }

        if (player.hasPermission("lobbyguard.*")) {
            return true;
        }

        if (player.isOp()) {
            return true;
        }

        return false;
    }

    // 常用权限常量
    public static final String PERM_BREAK = "lobbyguard.break";
    public static final String PERM_PLACE = "lobbyguard.place";
    public static final String PERM_DROP = "lobbyguard.drop";
    public static final String PERM_PICKUP = "lobbyguard.pickup";
    public static final String PERM_ADVENTURE = "lobbyguard.adventure";
    public static final String PERM_INVENTORY = "lobbyguard.inventory";
    public static final String PERM_ADMIN = "lobbyguard.admin";
}