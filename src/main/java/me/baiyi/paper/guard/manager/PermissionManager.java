package me.baiyi.paper.guard.manager;

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

        if (player.hasPermission("guard.*")) {
            return true;
        }

        if (player.isOp()) {
            return true;
        }

        return false;
    }

    // 常用权限常量
    public static final String PERM_BREAK = "guard.break";
    public static final String PERM_PLACE = "guard.place";
    public static final String PERM_DROP = "guard.drop";
    public static final String PERM_PICKUP = "guard.pickup";
    public static final String PERM_ADVENTURE = "guard.adventure";
    public static final String PERM_INVENTORY = "guard.inventory";
    public static final String PERM_ADMIN = "guard.admin";
}