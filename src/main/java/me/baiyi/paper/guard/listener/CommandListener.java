package me.baiyi.paper.guard.listener;

import me.baiyi.paper.guard.manager.ConfigManager;
import me.baiyi.paper.guard.manager.FeatureManager;
import me.baiyi.paper.guard.manager.MessageManager;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class CommandListener implements CommandExecutor {
    private final ConfigManager configManager;
    private final FeatureManager featureManager;
    private final MessageManager messageManager;

    public CommandListener(ConfigManager configManager, FeatureManager featureManager, MessageManager messageManager) {
        this.configManager = configManager;
        this.featureManager = featureManager;
        this.messageManager = messageManager;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (sender instanceof Player) {
            Player player = (Player) sender;
            if (!player.hasPermission("guard.admin")) {
                player.sendMessage(messageManager.getMessage("messages.no-permission"));
                return true;
            }
        }

        if (args.length < 1 || !args[0].equalsIgnoreCase("reload")) {
            sender.sendMessage(messageManager.getMessage("messages.usage", "%prefix%§c用法: /guard reload"));
            return true;
        }

        // 重载配置
        configManager.reloadConfig();
        featureManager.reloadFeatures();
        messageManager.reloadMessages();

        sender.sendMessage(messageManager.getMessage("messages.reload-success"));
        return true;
    }
}