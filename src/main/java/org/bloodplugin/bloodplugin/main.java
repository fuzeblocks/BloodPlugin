package org.bloodplugin.bloodplugin;


import org.bukkit.plugin.java.JavaPlugin;



public class main extends JavaPlugin {

    @Override
    public void onEnable() {
        saveDefaultConfig();
         System.out.println("Blood is now enable");
         getServer().getPluginManager().registerEvents(new BloodListener(this), this);
         getCommand("reload").setExecutor(new ReloadCommand(this));
         getCommand("bandage").setExecutor(new BandageCommand(this));




    }

    @Override
    public void onDisable() {

    }
}












