package me.baiyi.paper.lobbyguard;

import org.bukkit.Bukkit;
import org.bukkit.GameMode;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.event.*;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.event.entity.CreatureSpawnEvent;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.entity.EntityPickupItemEvent;
import org.bukkit.event.entity.FoodLevelChangeEvent;
import org.bukkit.event.player.PlayerDropItemEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.plugin.java.JavaPlugin;

public class LobbyGuard extends JavaPlugin implements Listener {
    private static LobbyGuard instance;

    public static LobbyGuard getInstance() {
        return instance;
    }

    @Override
    public void onEnable() {
        instance = this;
        ConfigManager.getInstance();
        FeatureManager.getInstance();
        MessageManager.getInstance();
        Bukkit.getPluginManager().registerEvents(this, this);
        getLogger().info("LobbyGuard enabled!");
    }

    @Override
    public void onDisable() {
        getLogger().info("LobbyGuard disabled.");
    }

    @EventHandler
    public void onCreatureSpawn(CreatureSpawnEvent event) {
        if (!FeatureManager.getInstance().isCreatureSpawnEnabled()) {
            event.setCancelled(true);
        }
    }

    @EventHandler
    public void onBlockBreak(BlockBreakEvent event) {
        if (!FeatureManager.getInstance().isBlockBreakEnabled()) {
            Player player = event.getPlayer();
            if (!player.hasPermission("lobbyguard.bypass.break")) {
                event.setCancelled(true);
                player.sendMessage(MessageManager.getInstance().getMessage("messages.no-break", "§c你不能破坏方块！"));
            }
        }
    }

    @EventHandler
    public void onBlockPlace(BlockPlaceEvent event) {
        if (!FeatureManager.getInstance().isBlockPlaceEnabled()) {
            Player player = event.getPlayer();
            if (!player.hasPermission("lobbyguard.bypass.place")) {
                event.setCancelled(true);
                player.sendMessage(MessageManager.getInstance().getMessage("messages.no-place", "§c你不能放置方块！"));
            }
        }
    }

    @EventHandler
    public void onEntityDamage(EntityDamageEvent event) {
        if (!FeatureManager.getInstance().isPlayerDamageEnabled()) {
            if (event.getEntity() instanceof Player) {
                event.setCancelled(true);
            }
        }
    }

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
            if (!player.hasPermission("lobbyguard.bypass.drop")) {
                event.setCancelled(true);
            }
        }
    }

    @EventHandler
    public void onPlayerPickupItem(EntityPickupItemEvent event) {
        if (!FeatureManager.getInstance().isItemPickupEnabled()) {
            if (event.getEntity() instanceof Player) {
                Player player = (Player) event.getEntity();
                if (!player.hasPermission("lobbyguard.bypass.pickup")) {
                    event.setCancelled(true);
                }
            }
        }
    }

    @EventHandler
    public void onPlayerJoin(PlayerJoinEvent event) {
        Player player = event.getPlayer();
        if (FeatureManager.getInstance().isAdventureOnJoinEnabled()) {
            if (!player.hasPermission("lobbyguard.bypass.join")) {
                player.setGameMode(GameMode.ADVENTURE);
            }
        }
        if (FeatureManager.getInstance().isClearInventoryOnJoinEnabled()) {
            player.getInventory().clear();
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
