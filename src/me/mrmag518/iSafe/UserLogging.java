package me.mrmag518.iSafe;

import java.io.File;
import java.io.IOException;

import org.bukkit.OfflinePlayer;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;

public class UserLogging implements Listener {
    public UserLogging() {
        plugin.getServer().getPluginManager().registerEvents(this, plugin);
    }
    
    public static iSafe plugin;
    public UserLogging(iSafe instance)
    {
        plugin = instance;
    }
    
    @EventHandler
    public void LogUserToFile(PlayerJoinEvent event) {
        Player user = event.getPlayer();
        
        File userFile = new File(plugin.getDataFolder() + File.separator + "User Libraries" + File.separator + user.getName() + ".txt");
        
        //Start the generate libraries process if the user ain't in the libraries.
        if (!userFile.exists()) {
            plugin.log.info("[iSafe] Generating user libraries for: "+ user.getName() + ".");
            
            try {
                FileConfiguration uFile = YamlConfiguration.loadConfiguration(userFile);
                
                uFile.set("UserName", user.getName());
                uFile.set("DisplayName", user.getDisplayName());
                uFile.set("User_IP", event.getPlayer().getAddress().getAddress().toString().replace("/", ""));
                uFile.set("User_Entity_ID", user.getEntityId());
                
                uFile.save(userFile);
                plugin.log.info("[iSafe] Generated user libraries for: "+ user.getName() + ".");
            } catch (Exception e) {
                
                //Returning in error, give the errors message and the log.
                plugin.log.severe("[iSafe] Error generating user libraries for: "+ user.getName() + ".");
                
                e.printStackTrace();
            }
        } else {
            String UserIP = event.getPlayer().getAddress().getAddress().toString();
            UserIP = UserIP.replace("/", "");
            
            UserString(event.getPlayer(), UserIP, "User_IP");
        }
    }
    
    public static void UserString(OfflinePlayer ofpl, String value, String path) {
        
        File UserOneData = new File(plugin.getDataFolder() + File.separator + "User Libraries" + File.separator + ofpl.getName() + ".txt");
        
        if (UserOneData.exists()) {
            FileConfiguration pFile = YamlConfiguration.loadConfiguration(UserOneData);
            pFile.set(path, value);
            try {
                pFile.save(UserOneData);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}