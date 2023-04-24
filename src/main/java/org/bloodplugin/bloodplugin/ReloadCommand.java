package org.bloodplugin.bloodplugin;

import com.sk89q.worldedit.bukkit.BukkitAdapter;
import com.sk89q.worldguard.bukkit.WorldGuardPlugin;
import net.md_5.bungee.api.ChatColor;
import org.bukkit.Particle;
import org.bukkit.Sound;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;
import java.util.Objects;
import com.sk89q.worldguard.WorldGuard;

import static sun.audio.AudioPlayer.player;

public class ReloadCommand implements CommandExecutor {
    private final JavaPlugin plugin;
    //Constructeur plugin
    public ReloadCommand(JavaPlugin plugin)
    {
        this.plugin = plugin;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {


        //Variable config
        FileConfiguration config = plugin.getConfig();
        //Vérification d'un joueur
        if (!(sender instanceof Player)) {

            sender.sendMessage(ChatColor.translateAlternateColorCodes('&',Objects.requireNonNull(config.getString("messages.notplayer"))));
            return true;
        }
        //Vérification de permission
        Player player = (Player) sender;
        if (!player.hasPermission("bloodreload.use")) {

            player.sendMessage((ChatColor.translateAlternateColorCodes('&',ChatColor.RED + config.getString("messages.reload"))));
            return true;
        }
        //Reload message et title
        plugin.reloadConfig();
        player.playSound(player.getLocation(), Sound.valueOf(config.getString("options.reloadsoundtype")),config.getInt("options.reloadsoundvolume"),config.getInt("options.reloadpitchvolume"));
        player.sendMessage(ChatColor.translateAlternateColorCodes('&', Objects.requireNonNull(config.getString("messages.reload"))));
        player.spawnParticle(Particle.valueOf(config.getString("options.reloadparticuletype")), player.getLocation(), config.getInt("options.reloadparticules"));
        player.sendTitle(
                ChatColor.translateAlternateColorCodes('&', Objects.requireNonNull(config.getString("messages.title"))),
                ChatColor.translateAlternateColorCodes('&', Objects.requireNonNull(config.getString("messages.subtitle"))),
                config.getInt("options.titlefadein"),
                config.getInt("options.titlestay"),
                config.getInt("options.titlefadeout")

        );

        return true;
    }
}
