package org.bloodplugin.bloodplugin;

import net.md_5.bungee.api.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;

public class InfoCommand implements CommandExecutor {
    private Plugin plugin;
    public InfoCommand(Main plugin) {
        this.plugin = plugin;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        FileConfiguration config = plugin.getConfig();
        Player player = (Player) sender;
        if(!player.hasPermission("bloodinfo.use")) {
            player.sendMessage(ChatColor.translateAlternateColorCodes('&',config.getString("messages.info")));
            return false;
        } else {
            player.sendMessage(ChatColor.GOLD + "------BloodPlugin-info------");
            player.sendMessage(ChatColor.GREEN + " Version : " +plugin.getDescription().getVersion());
            player.sendMessage(ChatColor.YELLOW + " Authors : " +plugin.getDescription().getAuthors());
            player.sendMessage(ChatColor.GREEN + " APIVersion : " +plugin.getDescription().getAPIVersion());
            player.sendMessage(ChatColor.RED + "------BloodPlugin-perms------");
            player.sendMessage(ChatColor.GREEN + "Give bandage permission: bloodbandage.use");
            player.sendMessage(ChatColor.YELLOW + "Reload permission = bloodreload.use");
            player.sendMessage(ChatColor.GREEN + "Admin permission = invinsible.secret.bloodplugin");
            player.sendMessage(ChatColor.GOLD + "----------------------");




        }

        return true;
    }
}
