package com.mrmag518.iSafe.Util;

import com.mrmag518.iSafe.Files.Messages;
import com.mrmag518.iSafe.Files.iSafeConfig;
import com.mrmag518.iSafe.iSafe;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class PermHandler {
    public static boolean checkingUpdatePerms = false;
    public static boolean cancelDamagePerms = false;
    public static boolean checkingSpamPerms = false;
    public static boolean checkingFullbrightPerms = false;
    
    public static boolean hasPermission(CommandSender sender, String permission) {
        if (iSafeConfig.getISafeConfig().getBoolean("UseVaultForPermissions", true)) {
            if (iSafe.perms.has(sender, permission)) {
                return true;
            } else {
                Messages.sendNoPermissionNotify(sender);
                return false;
            }
        } else {
            if (sender.hasPermission(permission)) {
                return true;
            } else {
                Messages.sendNoPermissionNotify(sender);
                return false;
            }
        }
    }

    public static boolean hasBlacklistPermission(Player p, String permission) {
        if (iSafeConfig.getISafeConfig().getBoolean("UseVaultForPermissions", true)) {
            if (iSafe.perms.has(p, permission)) {
                return true;
            } else {
                return false;
            }
        } else {
            if (p.hasPermission(permission)) {
                return true;
            } else {
                return false;
            }
        }
    }

    public static boolean hasPermission(Player p, String permission) {
        if (iSafeConfig.getISafeConfig().getBoolean("UseVaultForPermissions", true)) {
            if (iSafe.perms.has(p, permission)) {
                return true;
            } else {
                if(shallOutputNoPerm() == false) {
                    // ignore.
                } else {
                    Messages.sendNoPermissionNotify(p);
                }
                return false;
            }
        } else {
            if (p.hasPermission(permission)) {
                return true;
            } else {
                if(shallOutputNoPerm() == false) {
                    // ignore.
                } else {
                    Messages.sendNoPermissionNotify(p);
                }
                return false;
            }
        }
    }
    
    private static boolean shallOutputNoPerm() {
        if(checkingUpdatePerms == true
            || checkingSpamPerms == true
            || checkingFullbrightPerms == true) {
            
            checkingUpdatePerms = false;
            checkingSpamPerms = false;
            checkingFullbrightPerms = false;
            
            return false;
        } else {
            return true;
        }
    }
}
