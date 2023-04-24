package org.bloodplugin.bloodplugin;

import net.md_5.bungee.api.ChatColor;
import org.bukkit.Color;
import org.bukkit.Material;
import org.bukkit.Particle;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import java.util.Objects;

public class BloodListener implements Listener {
    private boolean shouldListen = true;
    private final JavaPlugin plugin;

    public BloodListener(JavaPlugin plugin) {
        this.plugin = plugin;
    }

    public void setShouldListen(boolean shouldListen) {
        this.shouldListen = shouldListen;
    }

    @EventHandler
    public void onEntityDamage(EntityDamageEvent event) {
        if (!shouldListen || !(event.getEntity() instanceof Player)) {
            return;
        }
        Player player = (Player) event.getEntity();
        if (event.getCause() == EntityDamageEvent.DamageCause.FALL) {
            // Récupérer les valeurs de configuration
            FileConfiguration config = plugin.getConfig();
            String potionType = config.getString("options.potion-type");
            int duration = config.getInt("options.potion-duration");
            int amplifier = config.getInt("options.potion-amplifier");

            // Créer l'effet de potion
            PotionEffectType effectType = PotionEffectType.getByName(potionType);
            if (effectType == null) {
                return;
            }
            PotionEffect effect = new PotionEffect(effectType, duration, amplifier);

            // Appliquer l'effet de potion au joueur
            player.addPotionEffect(effect);

            // Envoyer un message au joueur
            String bloodMessage = config.getString("messages.bloodmessage");
            if (bloodMessage != null) {
                player.sendMessage(ChatColor.translateAlternateColorCodes('&', bloodMessage));
            }

            // Afficher des particules
            Particle.DustOptions dustOptions = new Particle.DustOptions(Color.fromRGB(90, 7, 25), 1.0F);
            player.spawnParticle(Particle.REDSTONE, player.getLocation(), config.getInt("options.particules"), dustOptions);
        }
    }

    @EventHandler
    public void onPlayerInteract(PlayerInteractEvent event) {
        if (!shouldListen) {
            return;
        }
        Player player = event.getPlayer();
        ItemStack item = event.getItem();

        if (item != null && item.getType() == Material.PAPER && item.hasItemMeta()) {
            ItemMeta meta = item.getItemMeta();

            if (meta.hasDisplayName() && ChatColor.stripColor(meta.getDisplayName()).equals(plugin.getConfig().getString("items.bandagename"))) {
                // Annuler l'événement
                event.setCancelled(true);

                // Soigner le joueur
                player.addPotionEffect(new PotionEffect(PotionEffectType.getByName(plugin.getConfig().getString("items.healeffect")),plugin.getConfig().getInt("items.healduration"),plugin.getConfig().getInt("items.heallvl")));

                // Envoyer un message au joueur
                String bandageMessage = ChatColor.translateAlternateColorCodes('&', Objects.requireNonNull(plugin.getConfig().getString("messages.bandageuse")));
                if (bandageMessage != null) {
                    player.sendMessage(bandageMessage);
                }

                // Retirer un bandage de l'inventaire du joueur
                int amount = item.getAmount();
                if (amount > 1) {
                    item.setAmount(amount - 1);
                } else {
                    player.getInventory().remove(item);


                }
            }
        }
    }
}
