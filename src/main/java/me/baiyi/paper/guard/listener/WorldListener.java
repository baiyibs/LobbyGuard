package me.baiyi.paper.guard.listener;

import me.baiyi.paper.guard.manager.FeatureManager;
import me.baiyi.paper.guard.manager.MessageManager;
import me.baiyi.paper.guard.manager.PermissionManager;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.event.entity.CreatureSpawnEvent;
import org.bukkit.event.entity.EntityDamageEvent;

public class WorldListener implements Listener {

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
            if (!PermissionManager.getInstance().hasPermission(player, PermissionManager.PERM_BREAK)) {
                event.setCancelled(true);
                player.sendMessage(MessageManager.getInstance().getMessage("messages.no-break", "§c你不能破坏方块！"));
            }
        }
    }

    @EventHandler
    public void onBlockPlace(BlockPlaceEvent event) {
        if (!FeatureManager.getInstance().isBlockPlaceEnabled()) {
            Player player = event.getPlayer();
            if (!PermissionManager.getInstance().hasPermission(player, PermissionManager.PERM_PLACE)) {
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
}