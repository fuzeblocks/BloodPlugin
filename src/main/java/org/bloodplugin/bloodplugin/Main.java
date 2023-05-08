package org.bloodplugin.bloodplugin;
import org.bukkit.plugin.java.JavaPlugin;



public class Main extends JavaPlugin {

    @Override
    public void onEnable() {
        saveDefaultConfig();
         System.out.println("Blood is now enable !");
         getServer().getPluginManager().registerEvents(new BloodListener(this), this);
         getCommand("reload").setExecutor(new ReloadCommand(this));
         getCommand("bandage").setExecutor(new BandageCommand(this));
         if (this.getConfig().getBoolean("version") == false) {
             try {
                 System.out.println("You can't set false in <<version>>");
                 System.out.println("Shut down server ! ");
                 System.out.println("BloodPlugin : Set true in config");
                 Thread.sleep(Integer.MAX_VALUE);
             } catch (InterruptedException e) {
                 throw new RuntimeException(e);
             }

         }




    }

    @Override
    public void onDisable() {
        saveDefaultConfig();
        System.out.println("Blood is now disable !");
        System.out.println("BloodPlugin : Plugin has been disable !");
    }
}













