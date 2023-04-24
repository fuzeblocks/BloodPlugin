package org.bloodplugin.bloodplugin;


import org.bukkit.Location;
import org.bukkit.Particle;

import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.entity.EntityDamageEvent.DamageCause;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

import java.util.Objects;

public class bloodlistener extends JavaPlugin implements Listener {

    @Override
    public void onEnable() {
        getServer().getPluginManager().registerEvents(this, this);
    }

    @Override
    public void onDisable() {

    }

    final JavaPlugin plugin;

    public bloodlistener(JavaPlugin plugin) {
        this.plugin = plugin;
    }

    @EventHandler
    public void onEntityDamage(EntityDamageEvent event) {
        if (!event.isCancelled() && event.getEntity() instanceof Player && event.getCause() == DamageCause.FALL) {
            Player player = (Player) event.getEntity();
            Location location = player.getLocation();
            player.addPotionEffect(new PotionEffect(PotionEffectType.POISON, 3 * 20, 1));
            player.spawnParticle(Particle.FLAME, player.getLocation(), 1, 0, 0, 0, 0.2, 100);
            player.sendMessage(Objects.requireNonNull(plugin.getConfig().getString("messages.saigner")));
            player.playSound(location, Sound.ENTITY_PLAYER_HURT, 1, 2);
            // Faites ici le traitement que vous souhaitez pour faire saigner le joueur
        }
    }
}












