package me.baiyi.paper.guard.listener;

import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.List;

public class CommandTabCompleter implements TabCompleter {
    @Override
    public List<String> onTabComplete(CommandSender sender, Command command, String alias, String[] args) {
        List<String> completions = new ArrayList<>();

        if (sender instanceof Player && !sender.hasPermission("guard.admin")) {
            return completions;
        }

        if (args.length == 1) {
            completions.add("reload");
        }

        return completions;
    }
}