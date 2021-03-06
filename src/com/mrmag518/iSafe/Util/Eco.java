package com.mrmag518.iSafe.Util;

import com.mrmag518.iSafe.Files.BlacklistsF;
import com.mrmag518.iSafe.Files.Messages;
import com.mrmag518.iSafe.iSafe;
import org.bukkit.entity.Player;

public class Eco {
    public static void takeMoney(String player, String world, String blacklist, int amount) {
        if(amount < 1) {
            Log.severe("The blacklist " + blacklist + "'s amount of money to withdraw, is below 1.");
            return;
        }
        if(iSafe.economy.getBalance(player) < amount) {
            if(BlacklistsF.getBlacklists().getBoolean(blacklist + "." + world + ".Economy.AllowNegativeResult") == true) {
                iSafe.economy.withdrawPlayer(player, amount);
            } else {
                Log.severe("Could not withdraw " + amount + " money from " + player + " in world " + world + ", because the player's balance would go negative "
                        + ". You can enable support for negative results in the blacklists. This error was related to the blacklist: " + blacklist);
            }
        } else {
            iSafe.economy.withdrawPlayer(player, amount);
        }
    }
    
    public static void sendEcoNotify(Player p, String blacklist, int amount) {
        p.sendMessage(Messages.colorize(Messages.getMessages().getString("Blacklists." + blacklist + ".EcoMessage").replace("%amount%", String.valueOf(amount))));
    }
}
