/*
 * iSafe
 * Copyright (C) 2011-2012 mrmag518 <magnusaub@yahoo.no>
 * 
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 * 
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
 * GNU General Public License for more details.
 * 
 * You should have received a copy of the GNU General Public License
 * along with this program. If not, see <http://www.gnu.org/licenses/>.
 */

package com.mrmag518.iSafe.Blacklists;

import com.mrmag518.iSafe.iSafe;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;

public class Censor implements Listener {
    public static iSafe plugin;
    public Censor(iSafe instance)
    {
        plugin = instance;
        plugin.getServer().getPluginManager().registerEvents(this, plugin);
    }
    
    @EventHandler
    public void Censor(AsyncPlayerChatEvent event) {
        if (event.isCancelled()){
            return;
        }
        Player p = event.getPlayer();
        String sentence = event.getMessage().toLowerCase();
        
        
        for(String word : plugin.getBlacklist().getStringList("Censor.Words/Blacklist")) {
            if(sentence.contains(word)) {
                event.setCancelled(true);
                if (plugin.getBlacklist().getBoolean("Censor.Alert/log.ToConsole", true)){
                    if (event.isCancelled()) {
                        plugin.log.info("[iSafe] " + p.getName() + "'s message contained the blacklisted word: " + word);
                    }
                }
                
                if (plugin.getBlacklist().getBoolean("Censor.Alert/log.ToPlayer", true)){
                    if (event.isCancelled()) {
                        p.sendMessage(plugin.blacklistCensorMsg(word));
                    }
                }
                
                if (plugin.getBlacklist().getBoolean("Censor.KickPlayer", true)){
                    if (event.isCancelled()) {
                        p.kickPlayer(plugin.blacklistCensorKickMsg(word));
                    }
                }
            }
        }
    }
}

