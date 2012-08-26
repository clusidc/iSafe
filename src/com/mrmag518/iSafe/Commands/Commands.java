package com.mrmag518.iSafe.Commands;

import com.mrmag518.iSafe.iSafe;
import java.io.File;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.plugin.PluginDescriptionFile;

public class Commands implements CommandExecutor {
    public static iSafe plugin;
    public Commands(iSafe instance)
    {
        plugin = instance;
    }
    public ChatColor A = ChatColor.AQUA;
    public ChatColor G = ChatColor.GREEN;
    public ChatColor GR = ChatColor.GRAY;
    
    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String l, String[] args){
        if(l.equalsIgnoreCase("iSafe")) {
            if(sender instanceof Player) {
                if(args.length == 0) {
                    sender.sendMessage(A + "---iSafe Commands---");
                    sender.sendMessage(G + "/iSafe reload" + GR + " Reloads all the iSafe configuration files.");
                    sender.sendMessage(G + "/iSafe info " + GR + " Returns information about iSafe.");
                    sender.sendMessage(G + "/iSafe serverinfo " + GR + " Returns information about the server.");
                } else if (args.length == 1) {
                    if(args[0].equalsIgnoreCase("reload")) {
                        if(sender.hasPermission("iSafe.command.reload")) {
                            return reload(sender);
                        } else {
                            plugin.noCmdPermission(sender);
                        }
                    } else if(args[0].equalsIgnoreCase("info")) {
                        if(sender.hasPermission("iSafe.command.info")) {
                            return info(sender);
                        } else {
                            plugin.noCmdPermission(sender);
                        }
                    } else if(args[0].equalsIgnoreCase("serverinfo")) {
                        if(sender.hasPermission("iSafe.command.serverinfo")) {
                            return serverinfo(sender);
                        } else {
                            plugin.noCmdPermission(sender);
                        }
                    }
                }
            } else {
                if(args.length == 0) {
                    sender.sendMessage(A + "---iSafe Commands---");
                    sender.sendMessage(G + "/iSafe reload" + GR + " Reload all the iSafe configuration files.");
                    sender.sendMessage(G + "/iSafe info " + GR + " Returns information about iSafe.");
                    sender.sendMessage(G + "/iSafe serverinfo " + GR + " Returns information about the server.");
                } else if (args.length == 1) {
                    if(args[0].equalsIgnoreCase("reload")) {
                        return reload(sender);
                    } else if(args[0].equalsIgnoreCase("info")) {
                        return info(sender);
                    } else if(args[0].equalsIgnoreCase("serverinfo")) {
                        return serverinfo(sender);
                    }
                }
            }
        }
        return false;
    }
    
    public boolean reload(CommandSender sender) {
        String v = plugin.getDescription().getVersion();
        sender.sendMessage(G + "Reloading all iSafe " + v + " files ..");
        
        try{
            plugin.fileLoadManagement();
        }catch(Exception e) {
            sender.sendMessage(ChatColor.RED + "An issue ocurred while reloading iSafe!");
            e.printStackTrace();
        }
        
        sender.sendMessage(G + "Reloaded all iSafe " + v + " files.");
        return true;
    }
    
    public boolean info(CommandSender sender) {
        ChatColor AQ = ChatColor.AQUA;
        ChatColor W = ChatColor.WHITE;
        PluginDescriptionFile pdf = plugin.getDescription();
        sender.sendMessage(AQ + "--- iSafe Information ---");
        sender.sendMessage(AQ + "Author: " + W + "mrmag518");
        sender.sendMessage(AQ + "Testers: " + W + "domingo15 and Gunnerrrrr");
        sender.sendMessage(AQ + "Version: " + W + pdf.getFullName());
        sender.sendMessage(AQ + "Minecraft version: " + W + "1.3.*");
        sender.sendMessage(AQ + "BukkitDev link: " + W + "http://dev.bukkit.org/server-mods/blockthattnt/");
        return true;
    }
    
    public boolean serverinfo(CommandSender sender) {
        ChatColor AQ = ChatColor.AQUA;
        ChatColor W = ChatColor.WHITE;
        sender.sendMessage(AQ + "Bukkit version: "+ W + plugin.getServer().getBukkitVersion().toString());
        sender.sendMessage(AQ + "Server Address: "+ W + plugin.getServer().getIp().toString());
        sender.sendMessage(AQ + "Server name: "+ W + plugin.getServer().getName().toString());
        sender.sendMessage(AQ + "Default GameMode: "+ W + plugin.getServer().getDefaultGameMode().toString());
        sender.sendMessage(AQ + "Server port: "+ W + plugin.getServer().getPort());
        sender.sendMessage(AQ + "Spawn radius: "+ W + plugin.getServer().getSpawnRadius());
        sender.sendMessage(AQ + "Number of worlds: "+ W + plugin.getServer().getWorlds().size());
        return true;
    }
}
