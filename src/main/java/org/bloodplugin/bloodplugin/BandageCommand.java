package org.bloodplugin.bloodplugin;

import net.md_5.bungee.api.ChatColor;
import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.Arrays;

public class BandageCommand implements CommandExecutor, Listener {
    private final JavaPlugin plugin;

    public BandageCommand(JavaPlugin plugin) {
        this.plugin = plugin;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        FileConfiguration config = plugin.getConfig();
        if (sender instanceof Player) { // Vérifier si l'émetteur est un joueur
            Player player = (Player) sender;
            if (!player.hasPermission("bloodbandage.use")) {
                player.sendMessage(ChatColor.translateAlternateColorCodes('&', config.getString("messages.errorbandagegive")));
                return true;
            }

            // Créer un bandage et le mettre dans la main du joueur
            ItemStack bandage = new ItemStack(Material.PAPER, 1);
            ItemMeta bandageMeta = bandage.getItemMeta();
            bandageMeta.setDisplayName(ChatColor.translateAlternateColorCodes('&', config.getString("items.bandagename")));
            bandageMeta.setLore(Arrays.asList(ChatColor.translateAlternateColorCodes('&', config.getString("items.bandagelore1")), ChatColor.translateAlternateColorCodes('&', config.getString("items.bandagelore2"))));
            bandage.setItemMeta(bandageMeta);
            player.getInventory().addItem(bandage);

            // Mettre à jour l'inventaire du joueur
            player.updateInventory();

        }
        return true;
    }

    @EventHandler
    public void onPlayerInteract(PlayerInteractEvent event) {
        Player player = event.getPlayer();

        // Vérifier si l'action est un clic droit
        if (event.getAction() == Action.RIGHT_CLICK_AIR || event.getAction() == Action.RIGHT_CLICK_BLOCK) {

            // Vérifier si l'objet dans la main du joueur est un bandage
            if (player.getInventory().getItemInMainHand().getType() == Material.PAPER && ChatColor.stripColor(player.getInventory().getItemInMainHand().getItemMeta().getDisplayName()).equals(ChatColor.stripColor(plugin.getConfig().getString("items.bandagename")))) {

                // Annuler l'événement pour empêcher l'utilisation du bandage
                event.setCancelled(true);

            }
        }
    }
}
