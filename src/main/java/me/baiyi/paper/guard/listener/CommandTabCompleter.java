package me.baiyi.paper.guard.listener;

import me.baiyi.paper.guard.manager.WhitelistManager;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class CommandTabCompleter implements TabCompleter {
    private final WhitelistManager whitelistManager;

    public CommandTabCompleter() {
        this.whitelistManager = WhitelistManager.getInstance();
    }

    @Override
    public List<String> onTabComplete(CommandSender sender, Command command, String alias, String[] args) {
        if (!(sender instanceof Player)) {
            return null;
        }

        Player player = (Player) sender;
        if (!player.hasPermission("guard.admin")) {
            return null;
        }

        List<String> completions = new ArrayList<>();

        if (args.length == 1) {
            completions.addAll(Arrays.asList("add", "remove", "list", "reload"));
            return filterCompletions(completions, args[0]);
        } else if (args.length == 2) {
            String subCommand = args[0].toLowerCase();
            if (subCommand.equals("remove")) {
                completions.addAll(whitelistManager.getWhitelistPlayers());
                return filterCompletions(completions, args[1]);
            }
        }

        return completions;
    }

    private List<String> filterCompletions(List<String> completions, String input) {
        List<String> filteredCompletions = new ArrayList<>();
        String lowerInput = input.toLowerCase();

        for (String completion : completions) {
            if (completion.toLowerCase().startsWith(lowerInput)) {
                filteredCompletions.add(completion);
            }
        }

        return filteredCompletions;
    }
}