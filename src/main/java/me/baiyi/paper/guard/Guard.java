package me.baiyi.paper.guard;

import me.baiyi.paper.guard.listener.PlayerListener;
import me.baiyi.paper.guard.listener.WorldListener;
import me.baiyi.paper.guard.listener.CommandListener;
import me.baiyi.paper.guard.listener.CommandTabCompleter;
import me.baiyi.paper.guard.manager.ConfigManager;
import me.baiyi.paper.guard.manager.FeatureManager;
import me.baiyi.paper.guard.manager.MessageManager;
import me.baiyi.paper.guard.manager.PermissionManager;
import me.baiyi.paper.guard.manager.WhitelistManager;
import org.bukkit.Bukkit;
import org.bukkit.command.PluginCommand;
import org.bukkit.plugin.java.JavaPlugin;

public class Guard extends JavaPlugin {
    private static Guard instance;

    public static Guard getInstance() {
        return instance;
    }

    @Override
    public void onEnable() {
        instance = this;
        
        // 初始化管理器
        ConfigManager configManager = ConfigManager.getInstance();
        FeatureManager featureManager = FeatureManager.getInstance();
        MessageManager messageManager = MessageManager.getInstance();
        PermissionManager.getInstance();
        WhitelistManager.getInstance();

        // 注册监听器
        Bukkit.getPluginManager().registerEvents(new PlayerListener(), this);
        Bukkit.getPluginManager().registerEvents(new WorldListener(), this);

        // 注册命令执行器和自动补全
        PluginCommand command = this.getCommand("guard");
        if (command != null) {
            command.setExecutor(new CommandListener(configManager, featureManager, messageManager));
            command.setTabCompleter(new CommandTabCompleter());
        }

        getLogger().info("Guard enabled!");
    }

    @Override
    public void onDisable() {
        getLogger().info("Guard disabled.");
    }
}