package me.baiyi.paper.lobbyguard.listener;

import me.baiyi.paper.lobbyguard.manager.ConfigManager;
import me.baiyi.paper.lobbyguard.manager.FeatureManager;
import me.baiyi.paper.lobbyguard.manager.MessageManager;
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
        if (!(sender instanceof Player)) {
            sender.sendMessage("该命令只能由玩家执行！");
            return true;
        }

        Player player = (Player) sender;

        if (!player.hasPermission("lobbyguard.admin")) {
            player.sendMessage(messageManager.getMessage("no-permission"));
            return true;
        }

        if (args.length < 1 || !args[0].equalsIgnoreCase("reload")) {
            player.sendMessage("§c用法: /guard reload");
            return true;
        }

        // 重载配置
        configManager.reloadConfig();
        featureManager.reloadFeatures();
        messageManager.reloadMessages();

        player.sendMessage(messageManager.getMessage("reload-success"));
        return true;
    }
}