package me.baiyi.paper.guard.listener;

import me.baiyi.paper.guard.manager.FeatureManager;
import me.baiyi.paper.guard.manager.MessageManager;
import me.baiyi.paper.guard.manager.PermissionManager;
import org.bukkit.Bukkit;
import org.bukkit.GameMode;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityPickupItemEvent;
import org.bukkit.event.entity.FoodLevelChangeEvent;
import org.bukkit.event.player.PlayerDropItemEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;

public class PlayerListener implements Listener {

    @EventHandler
    public void onHunger(FoodLevelChangeEvent event) {
        if (!FeatureManager.getInstance().isHungerEnabled()) {
            if (event.getEntity() instanceof Player) {
                event.setCancelled(true);
            }
        }
    }

    @EventHandler
    public void onPlayerDropItem(PlayerDropItemEvent event) {
        if (!FeatureManager.getInstance().isItemDropEnabled()) {
            Player player = event.getPlayer();
            if (!PermissionManager.getInstance().hasPermission(player, PermissionManager.PERM_DROP)) {
                event.setCancelled(true);
            }
        }
    }

    @EventHandler
    public void onPlayerPickupItem(EntityPickupItemEvent event) {
        if (!FeatureManager.getInstance().isItemPickupEnabled()) {
            if (event.getEntity() instanceof Player) {
                Player player = (Player) event.getEntity();
                if (!PermissionManager.getInstance().hasPermission(player, PermissionManager.PERM_PICKUP)) {
                    event.setCancelled(true);
                }
            }
        }
    }

    @EventHandler
    public void onPlayerJoin(PlayerJoinEvent event) {
        Player player = event.getPlayer();
        if (FeatureManager.getInstance().isAdventureOnJoinEnabled()) {
            if (!PermissionManager.getInstance().hasPermission(player, PermissionManager.PERM_ADVENTURE)) {
                player.setGameMode(GameMode.ADVENTURE);
            }
        }
        if (FeatureManager.getInstance().isClearInventoryOnJoinEnabled()) {
            if (!PermissionManager.getInstance().hasPermission(player, PermissionManager.PERM_INVENTORY)) {
                player.getInventory().clear();
            }
        }
        event.setJoinMessage(MessageManager.getInstance().getMessage("messages.join", "§a%player% 加入了游戏！").replace("%player%", player.getName()));
        player.teleport(new Location(Bukkit.getWorld("world"),0.50,86.50,0.50));
        player.setAllowFlight(true);
        player.sendTitle("§6欢迎来到MCDDW", "§e开始你的游戏吧！", 10, 70, 20);
    }

    @EventHandler
    public void onPlayerQuit(PlayerQuitEvent event) {
        Player player = event.getPlayer();
        event.setQuitMessage(MessageManager.getInstance().getMessage("messages.quit", "§c%player% 离开了游戏！").replace("%player%", player.getName()));
        player.setAllowFlight(false);
    }
}