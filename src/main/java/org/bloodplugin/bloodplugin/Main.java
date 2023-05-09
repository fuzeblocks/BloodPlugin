package org.bloodplugin.bloodplugin;

import org.bukkit.plugin.java.JavaPlugin;

public class Main extends JavaPlugin {

    @Override
    public void onEnable() {
        saveDefaultConfig();
        System.out.println("Blood is now enabled!");
        getServer().getPluginManager().registerEvents(new BloodListener(this), this);
        getCommand("reload").setExecutor(new ReloadCommand(this));
        getCommand("bandage").setExecutor(new BandageCommand(this));
        getCommand("info").setExecutor(new InfoCommand(this));
        if (getConfig().getBoolean("update.enabled")) {
            new UpdateChecker(this, 109180).getVersion(version -> {
                if (this.getDescription().getVersion().equals(version)) {
                    if (getConfig().getBoolean("update.updatemessage")) {
                        getLogger().info(getConfig().getString("update.noupdate"));
                    }
                } else {
                    if (getConfig().getBoolean("update.updatemessage")) {
                        getLogger().info(getConfig().getString("update.update"));
                    }
                }
            });
        } else {
            getLogger().info("Update is disabled.");
        }
    }

    @Override
    public void onDisable() {
        saveDefaultConfig();
        System.out.println("Blood is now disabled!");
        System.out.println("BloodPlugin: Plugin has been disabled!");
    }
}













