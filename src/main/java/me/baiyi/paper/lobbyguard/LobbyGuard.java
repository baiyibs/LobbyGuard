package me.baiyi.paper.lobbyguard;

import me.baiyi.paper.lobbyguard.listener.PlayerListener;
import me.baiyi.paper.lobbyguard.listener.WorldListener;
import me.baiyi.paper.lobbyguard.listener.CommandListener;
import me.baiyi.paper.lobbyguard.manager.ConfigManager;
import me.baiyi.paper.lobbyguard.manager.FeatureManager;
import me.baiyi.paper.lobbyguard.manager.MessageManager;
import me.baiyi.paper.lobbyguard.manager.PermissionManager;
import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;

public class LobbyGuard extends JavaPlugin {
    private static LobbyGuard instance;

    public static LobbyGuard getInstance() {
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

        // 注册监听器
        Bukkit.getPluginManager().registerEvents(new PlayerListener(), this);
        Bukkit.getPluginManager().registerEvents(new WorldListener(), this);

        // 注册命令执行器
        this.getCommand("guard").setExecutor(new CommandListener(configManager, featureManager, messageManager));

        getLogger().info("LobbyGuard enabled!");
    }

    @Override
    public void onDisable() {
        getLogger().info("LobbyGuard disabled.");
    }
}
