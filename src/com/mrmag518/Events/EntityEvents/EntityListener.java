package com.mrmag518.Events.EntityEvents;

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



import com.mrmag518.iSafe.*;

import org.bukkit.entity.*;
import org.bukkit.event.entity.*;
import org.bukkit.*;
import org.bukkit.block.Block;
import org.bukkit.entity.Wolf;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.CreeperPowerEvent.PowerCause;
import org.bukkit.event.entity.EntityDamageEvent.*;
import org.bukkit.event.entity.EntityRegainHealthEvent.RegainReason;
import org.bukkit.event.entity.EntityTargetEvent.*;


public class EntityListener implements Listener {
    public static iSafe plugin;
    public EntityListener(iSafe instance)
    {
        plugin = instance;
        plugin.getServer().getPluginManager().registerEvents(this, plugin);
    }
    
    @EventHandler
    public void managePortalCreation(EntityCreatePortalEvent event) {
        if (event.isCancelled())
        {
            return;
        }
        
        if(event.getEntity() instanceof Player) {
            Player p = (Player)event.getEntity();
            if(!(plugin.hasPermission(p, "iSafe.createportal"))) {
                event.setCancelled(true);
            }
        }
    }
    
    @EventHandler
    public void onExplosionPrime(ExplosionPrimeEvent event) {
        if (event.isCancelled())
        {
            return;
        }
        
        if(plugin.getConfig().getBoolean("Explosions.DisablePrimedExplosions", true))
        {
            event.getEntity().remove();
            event.setCancelled(true);
        }
    }
    
    @EventHandler
    public void onEntityExplode(EntityExplodeEvent event) {
        if (event.isCancelled())
        {
            return;
        }
        
        Entity ent = event.getEntity();
        int x = (int) event.getLocation().getX();
        int y = (int) event.getLocation().getY();
        int z = (int) event.getLocation().getZ();
        String world = ent.getWorld().getName();
        
        if(plugin.getConfig().getBoolean("Explosions.DisableAllExplosions", true))
        {
            int blocks = event.blockList().size();
            event.blockList().clear();
            if(plugin.getConfig().getBoolean("Explosions.DebugExplosions", true))
            {
                plugin.log.info("[iSafe](Debug)" + " An explosion was prevented at the location: X: "+ x + " Y: "+ y 
                        + " Z: "+ z + " | Yield: "+ event.getYield()
                        + " | Amount of blocks: " + blocks + " | World: "+ world + " | Caused by: Unknown.");
            }
            return;
        }
        
        if(plugin.getConfig().getBoolean("Explosions.DisableCreeperExplosions", true))
        {
            int blocks = event.blockList().size();
            if (ent instanceof Creeper) 
            {
                event.blockList().clear();
                
                if(plugin.getConfig().getBoolean("Explosions.DebugExplosions", true))
                {
                plugin.log.info("[iSafe](Debug)" + " An explosion was prevented at the location: X: "+ x + " Y: "+ y 
                        + " Z: "+ z + " | Yield: "+ event.getYield()
                        + " | Amount of blocks: " + blocks + " | World: "+ world + " | Caused by: Creeper.");
                }
                return;
            }
        }
        
        if(plugin.getConfig().getBoolean("Explosions.DisableEnderdragonBlockDamage", true))
        {
            int blocks = event.blockList().size();
            if (ent instanceof EnderDragon) 
            {
                event.blockList().clear();
                
                if(plugin.getConfig().getBoolean("Explosions.DebugExplosions", true))
                {
                plugin.log.info("[iSafe](Debug)" + " An explosion was prevented at the location: X: "+ x + " Y: "+ y 
                        + " Z: "+ z + " | Yield: "+ event.getYield()
                        + " | Amount of blocks: " + blocks + " | World: "+ world + " | Caused by: EnderDragon.");
                }
                return;
            }
        }
        
        if(plugin.getConfig().getBoolean("Explosions.DisableTntExplosions", true))
        {
            int blocks = event.blockList().size();
            if (ent instanceof TNTPrimed) 
            {
                event.blockList().clear();
                
                if(plugin.getConfig().getBoolean("Explosions.DebugExplosions", true))
                {
                plugin.log.info("[iSafe](Debug)" + " An explosion was prevented at the location: X: "+ x + " Y: "+ y 
                        + " Z: "+ z + " | Yield: "+ event.getYield()
                        + " | Amount of blocks: " + blocks + " | World: "+ world + " | Caused by: TNT.");
                }
                return;
            }
        }
        
        if(plugin.getConfig().getBoolean("Explosions.DisableFireballExplosions", true))
        {
            int blocks = event.blockList().size();
            if (ent instanceof Fireball) 
            {
                event.blockList().clear();
                
                if(plugin.getConfig().getBoolean("Explosions.DebugExplosions", true))
                {
                plugin.log.info("[iSafe](Debug)" + " An explosion was prevented at the location: X: "+ x + " Y: "+ y 
                        + " Z: "+ z + " | Yield: "+ event.getYield()
                        + " | Amount of blocks: " + blocks + " | World: "+ world + " | Caused by: Fireball.");
                }
                return;
            }  
        }
        
        if(plugin.getConfig().getBoolean("Explosions.DisableEnderCrystalExplosions", true))
        {
            int blocks = event.blockList().size();
            if (ent instanceof EnderCrystal) 
            {
                event.blockList().clear();
                
                if(plugin.getConfig().getBoolean("Explosions.DebugExplosions", true))
                {
                    plugin.log.info("[iSafe](Debug)" + " An explosion was prevented at the location: X: "+ x + " Y: "+ y 
                            + " Z: "+ z + " | Yield: "+ event.getYield()
                            + " | Amount of blocks: " + blocks + " | World: "+ world + " | Caused by: EnderCrystal.");
                }
                return;
            }  
        }
    }
    
    @EventHandler
    public void expBottleManagement(ExpBottleEvent event) {
        if(plugin.getConfig().getBoolean("Miscellaneous.PreventExpBottleThrow", true)) {
            event.getEntity().remove();
            event.setExperience(0);
            event.setShowEffect(false);
        }
    }
    
    @EventHandler
    public void EndermenGriefing(EntityChangeBlockEvent event) {
        if (event.isCancelled())
        {
            return;
        }
        
        Entity entity = event.getEntity();

        if(plugin.getCreatureManager().getBoolean("Creatures.Endermen.Prevent-Endermen-griefing", true))
        {
            if (entity instanceof Enderman) {
                event.setCancelled(true);
            }
        }
    }

    @EventHandler
    public void onEntityDamage(EntityDamageEvent event) {
        Entity entity = event.getEntity();
        World world = entity.getWorld();
        
        if(event.getCause() == DamageCause.VOID) {
            if(entity instanceof Player) {
                Player p = (Player)entity;
                if(event.isCancelled() || !event.isCancelled()) {
                    if(plugin.getConfig().getBoolean("VoidFall.TeleportPlayerToSpawn", true)) {
                        p.teleport(world.getSpawnLocation());
                    } else if(plugin.getConfig().getBoolean("VoidFall.TeleportPlayerBackAndFixHole", true)) {
                        int highestY = p.getWorld().getHighestBlockYAt(p.getLocation());
                        Location loc = new Location(p.getWorld(), p.getLocation().getX(), highestY+5, p.getLocation().getZ());
                        Block b = p.getWorld().getBlockAt(loc.getBlockX(), 0, loc.getBlockZ());
                        if(plugin.getConfig().getBoolean("VoidFall.FixHoleWithGlass", true)) {
                            b.setTypeId(20);
                        } else if(plugin.getConfig().getBoolean("VoidFall.FixHoleWithBedrock", true)) {
                            b.setTypeId(7);
                        } else {
                            plugin.getConfig().set("VoidFall.FixHoleWithGlass", true);
                            plugin.saveConfig();
                            b.setTypeId(20);
                        }
                        p.teleport(loc);
                    }
                }
            }
        }
        
        // Continue v3.0 ...
        
        if(plugin.getConfig().getBoolean("Entity-Damage.Disable-npc(Villagers)-death/damage", true))
        {
            if(entity instanceof Villager) 
            {
                event.setDamage(0);
                event.setCancelled(true);
            }
        }
        
        if(plugin.getConfig().getBoolean("Entity-Damage.Disable-player-death/damage", true))
        {
            if(entity instanceof Player) 
            {
                event.setDamage(0);
                event.setCancelled(true);
            }
        }
        
        if(plugin.getConfig().getBoolean("Explosions.Disable-(Block)Explosion-damage.To-Players", true))
        {
            if(event.getCause() == DamageCause.BLOCK_EXPLOSION) {
                if (entity instanceof Player) {
                    event.setCancelled(true);
                    event.setDamage(0);
                }
            }
        }
        
        if(plugin.getConfig().getBoolean("Explosions.Disable-(Block)Explosion-damage.To-Creatures", true))
        {
            if(event.getCause() == DamageCause.BLOCK_EXPLOSION) {
                if (entity instanceof Creature || entity instanceof Animals) {
                    event.setCancelled(true);
                    event.setDamage(0);
                }
            }
        }
        
        if(plugin.getConfig().getBoolean("Explosions.Disable-(Entity)Explosion-damage.To-Players", true))
        {
            if(event.getCause() == DamageCause.ENTITY_EXPLOSION) {
                if (entity instanceof Player) {
                    event.setCancelled(true);
                    event.setDamage(0);
                }
            }
        }
        
        if(plugin.getConfig().getBoolean("Explosions.Disable-(Entity)Explosion-damage.To-Creatures", true))
        {
            if(event.getCause() == DamageCause.ENTITY_EXPLOSION) {
                if (entity instanceof Creature || entity instanceof Animals) {
                    event.setCancelled(true);
                    event.setDamage(0);
                }
            }
        }
        
        if(plugin.getConfig().getBoolean("Entity-Damage.Players.Disable-Fire-damage", true))
        {
            if(event.getCause().equals(DamageCause.FIRE) || event.getCause().equals(DamageCause.FIRE_TICK)) {
                if (entity instanceof Player) {
                    if(plugin.getConfig().getBoolean("Entity-Damage.Enable-permissions", true)) {
                        Player player = (Player)entity;
                        if(plugin.hasPermission(player, "iSafe.canceldamage.fire")) {
                            event.setDamage(0);
                            event.setCancelled(true);
                        }
                    } else {
                        event.setDamage(0);
                        event.setCancelled(true);
                    }
                }
            }
        }
        if(plugin.getConfig().getBoolean("Entity-Damage.Creatures.Disable-Fire-damage", true))
        {
            if(event.getCause().equals(DamageCause.FIRE) || event.getCause().equals(DamageCause.FIRE_TICK)) {
                if (entity instanceof Creature || entity instanceof Animals) {
                    event.setCancelled(true);
                    event.setDamage(0);
                }
            }
        }
        
        if(plugin.getConfig().getBoolean("Entity-Damage.Players.Disable-Contact-damage", true))
        {
            if(event.getCause().equals(DamageCause.CONTACT)) {
                if (entity instanceof Player) {
                    if(plugin.getConfig().getBoolean("Entity-Damage.Enable-permissions", true)) {
                        Player player = (Player)entity;
                        if(plugin.hasPermission(player, "iSafe.canceldamage.contact")) {
                            event.setDamage(0);
                            event.setCancelled(true);
                        }
                    } else {
                        event.setDamage(0);
                        event.setCancelled(true);
                    }
                }
            }
        }
        if(plugin.getConfig().getBoolean("Entity-Damage.Creatures.Disable-Contact-damage", true))
        {
            if(event.getCause().equals(DamageCause.CONTACT)) {
                if (entity instanceof Creature || entity instanceof Animals) {
                    event.setDamage(0);
                    event.setCancelled(true);
                }
            }
        }
        
        if(plugin.getConfig().getBoolean("Entity-Damage.Players.Disable-Custom-damage", true))
        {
            if(event.getCause().equals(DamageCause.CUSTOM)) {
                if (entity instanceof Player) {
                    if(plugin.getConfig().getBoolean("Entity-Damage.Enable-permissions", true)) {
                        Player player = (Player)entity;
                        if(plugin.hasPermission(player, "iSafe.canceldamage.custom")) {
                            event.setDamage(0);
                            event.setCancelled(true);
                        }
                    } else {
                        event.setDamage(0);
                        event.setCancelled(true);
                    }
                }
            }
        }
        if(plugin.getConfig().getBoolean("Entity-Damage.Creatures.Disable-Custom-damage", true))
        {
            if(event.getCause().equals(DamageCause.CUSTOM)) {
                if (entity instanceof Creature || entity instanceof Animals) {
                    event.setDamage(0);
                    event.setCancelled(true);
                }
            }
        }
        
        if(plugin.getConfig().getBoolean("Entity-Damage.Players.Disable-Drowning-damage", true))
        {
            if(event.getCause().equals(DamageCause.DROWNING)) {
                if (entity instanceof Player) {
                    if(plugin.getConfig().getBoolean("Entity-Damage.Enable-permissions", true)) {
                        Player player = (Player)entity;
                        if(plugin.hasPermission(player, "iSafe.canceldamage.drowning")) {
                            event.setDamage(0);
                            event.setCancelled(true);
                        }
                    } else {
                        event.setDamage(0);
                        event.setCancelled(true);
                    }
                }
            }
        }
        if(plugin.getConfig().getBoolean("Entity-Damage.Creatures.Disable-Drowning-damage", true))
        {
            if(event.getCause().equals(DamageCause.DROWNING)) {
                if (entity instanceof Creature || entity instanceof Animals) {
                    event.setDamage(0);
                    event.setCancelled(true);
                }
            }
        }
        
        if(plugin.getConfig().getBoolean("Entity-Damage.Players.Disable-EntityAttack-damage", true))
        {
            if(event.getCause().equals(DamageCause.ENTITY_ATTACK)) {
                if (entity instanceof Player) {
                    if(plugin.getConfig().getBoolean("Entity-Damage.Enable-permissions", true)) {
                        Player player = (Player)entity;
                        if(plugin.hasPermission(player, "iSafe.canceldamage.entityattack")) {
                            event.setDamage(0);
                            event.setCancelled(true);
                        }
                    } else {
                        event.setDamage(0);
                        event.setCancelled(true);
                    }
                }
            }
        }
        if(plugin.getConfig().getBoolean("Entity-Damage.Creatures.Disable-EntityAttack-damage", true))
        {
            if(event.getCause().equals(DamageCause.ENTITY_ATTACK)) {
                if (entity instanceof Creature || entity instanceof Animals) {
                    event.setDamage(0);
                    event.setCancelled(true);
                }
            }
        }
        
        if(plugin.getConfig().getBoolean("Entity-Damage.Players.Disable-Fall-damage", true))
        {
            if(event.getCause().equals(DamageCause.FALL)) {
                if (entity instanceof Player) {
                    if(plugin.getConfig().getBoolean("Entity-Damage.Enable-permissions", true)) {
                        Player player = (Player)entity;
                        if(plugin.hasPermission(player, "iSafe.canceldamage.fall")) {
                            event.setDamage(0);
                            event.setCancelled(true);
                        }
                    } else {
                        event.setDamage(0);
                        event.setCancelled(true);
                    }
                }
            }
        }
        if(plugin.getConfig().getBoolean("Entity-Damage.Creatures.Disable-Fall-damage", true))
        {
            if(event.getCause().equals(DamageCause.FALL)) {
                if (entity instanceof Creature || entity instanceof Animals) {
                    event.setDamage(0);
                    event.setCancelled(true);
                }
            }
        }
        
        if(plugin.getConfig().getBoolean("Entity-Damage.Players.Disable-Lava-damage", true))
        {
            if(event.getCause().equals(DamageCause.LAVA)) {
                if (entity instanceof Player) {
                    if(plugin.getConfig().getBoolean("Entity-Damage.Enable-permissions", true)) {
                        Player player = (Player)entity;
                        if(plugin.hasPermission(player, "iSafe.canceldamage.lava")) {
                            event.setDamage(0);
                            event.setCancelled(true);
                        }
                    } else {
                        event.setDamage(0);
                        event.setCancelled(true);
                    }
                }
            }
        }
        if(plugin.getConfig().getBoolean("Entity-Damage.Creatures.Disable-Lava-damage", true))
        {
            if(event.getCause().equals(DamageCause.LAVA)) {
                if (entity instanceof Creature || entity instanceof Animals) {
                    event.setDamage(0);
                    event.setCancelled(true);
                }
            }
        }
        
        if(plugin.getConfig().getBoolean("Entity-Damage.Players.Disable-Lightning-damage", true))
        {
            if(event.getCause().equals(DamageCause.LIGHTNING)) {
                if (entity instanceof Player) {
                    if(plugin.getConfig().getBoolean("Entity-Damage.Enable-permissions", true)) {
                        Player player = (Player)entity;
                        if(plugin.hasPermission(player, "iSafe.canceldamage.lightning")) {
                            event.setDamage(0);
                            event.setCancelled(true);
                        }
                    } else {
                        event.setDamage(0);
                        event.setCancelled(true);
                    }
                }
            }
        }
        if(plugin.getConfig().getBoolean("Entity-Damage.Creatures.Disable-Lightning-damage", true))
        {
            if(event.getCause().equals(DamageCause.LIGHTNING)) {
                if (entity instanceof Creature || entity instanceof Animals) {
                    event.setDamage(0);
                    event.setCancelled(true);
                }
            }
        }
        
        if(plugin.getConfig().getBoolean("Entity-Damage.Players.Disable-Magic-damage", true))
        {
            if(event.getCause().equals(DamageCause.MAGIC)) {
                if (entity instanceof Player) {
                    if(plugin.getConfig().getBoolean("Entity-Damage.Enable-permissions", true)) {
                        Player player = (Player)entity;
                        if(plugin.hasPermission(player, "iSafe.canceldamage.magic")) {
                            event.setDamage(0);
                            event.setCancelled(true);
                        }
                    } else {
                        event.setDamage(0);
                        event.setCancelled(true);
                    }
                }
            }
        }
        if(plugin.getConfig().getBoolean("Entity-Damage.Creatures.Disable-Magic-damage", true))
        {
            if(event.getCause().equals(DamageCause.MAGIC)) {
                if (entity instanceof Creature || entity instanceof Animals) {
                    event.setDamage(0);
                    event.setCancelled(true);
                }
            }
        }
        
        if(plugin.getConfig().getBoolean("Entity-Damage.Players.Disable-Poison-damage", true))
        {
            if(event.getCause().equals(DamageCause.POISON)) {
                if (entity instanceof Player) {
                    if(plugin.getConfig().getBoolean("Entity-Damage.Enable-permissions", true)) {
                        Player player = (Player)entity;
                        if(plugin.hasPermission(player, "iSafe.canceldamage.poison")) {
                            event.setDamage(0);
                            event.setCancelled(true);
                        }
                    } else {
                        event.setDamage(0);
                        event.setCancelled(true);
                    }
                }
            }
        }
        if(plugin.getConfig().getBoolean("Entity-Damage.Creatures.Disable-Poison-damage", true))
        {
            if(event.getCause().equals(DamageCause.POISON)) {
                if (entity instanceof Creature || entity instanceof Animals) {
                    event.setDamage(0);
                    event.setCancelled(true);
                }
            }
        }
        
        if(plugin.getConfig().getBoolean("Entity-Damage.Players.Disable-Projectile-damage", true))
        {
            if(event.getCause().equals(DamageCause.PROJECTILE)) {
                if (entity instanceof Player) {
                    if(plugin.getConfig().getBoolean("Entity-Damage.Enable-permissions", true)) {
                        Player player = (Player)entity;
                        if(plugin.hasPermission(player, "iSafe.canceldamage.projectile")) {
                            event.setDamage(0);
                            event.setCancelled(true);
                        }
                    } else {
                        event.setDamage(0);
                        event.setCancelled(true);
                    }
                }
            }
        }
        if(plugin.getConfig().getBoolean("Entity-Damage.Creatures.Disable-Projectile-damage", true))
        {
            if(event.getCause().equals(DamageCause.PROJECTILE)) {
                if (entity instanceof Creature || entity instanceof Animals) {
                    event.setDamage(0);
                    event.setCancelled(true);
                }
            }
        }
        
        if(plugin.getConfig().getBoolean("Entity-Damage.Players.Disable-Starvation-damage", true))
        {
            if(event.getCause().equals(DamageCause.STARVATION)) {
                if (entity instanceof Player) {
                    if(plugin.getConfig().getBoolean("Entity-Damage.Enable-permissions", true)) {
                        Player player = (Player)entity;
                        if(plugin.hasPermission(player, "iSafe.canceldamage.starvation")) {
                            event.setDamage(0);
                            event.setCancelled(true);
                        }
                    } else {
                        event.setDamage(0);
                        event.setCancelled(true);
                    }
                }
            }
        }
        if(plugin.getConfig().getBoolean("Entity-Damage.Creatures.Disable-Starvation-damage", true))
        {
            if(event.getCause().equals(DamageCause.STARVATION)) {
                if (entity instanceof Creature || entity instanceof Animals) {
                    event.setDamage(0);
                    event.setCancelled(true);
                }
            }
        }
        
        if(plugin.getConfig().getBoolean("Entity-Damage.Players.Disable-Suffocation-damage", true))
        {
            if(event.getCause().equals(DamageCause.SUFFOCATION)) {
                if (entity instanceof Player) {
                    if(plugin.getConfig().getBoolean("Entity-Damage.Enable-permissions", true)) {
                        Player player = (Player)entity;
                        if(plugin.hasPermission(player, "iSafe.canceldamage.suffocation")) {
                            event.setDamage(0);
                            event.setCancelled(true);
                        }
                    } else {
                        event.setDamage(0);
                        event.setCancelled(true);
                    }
                }
            }
        }
        if(plugin.getConfig().getBoolean("Entity-Damage.Creatures.Disable-Suffocation-damage", true))
        {
            if(event.getCause().equals(DamageCause.SUFFOCATION)) {
                if (entity instanceof Creature || entity instanceof Animals) {
                    event.setDamage(0);
                    event.setCancelled(true);
                }
            }
        }
        
        if(plugin.getConfig().getBoolean("Entity-Damage.Players.Disable-Suicide-damage", true))
        {
            if(event.getCause().equals(DamageCause.SUICIDE)) {
                if (entity instanceof Player) {
                    if(plugin.getConfig().getBoolean("Entity-Damage.Enable-permissions", true)) {
                        Player player = (Player)entity;
                        if(plugin.hasPermission(player, "iSafe.canceldamage.suicide")) {
                            event.setDamage(0);
                            event.setCancelled(true);
                        }
                    } else {
                        event.setDamage(0);
                        event.setCancelled(true);
                    }
                }
            }
        }
        if(plugin.getConfig().getBoolean("Entity-Damage.Creatures.Disable-Suicide-damage", true))
        {
            if(event.getCause().equals(DamageCause.SUICIDE)) {
                if (entity instanceof Creature || entity instanceof Animals) {
                    event.setDamage(0);
                    event.setCancelled(true);
                }
            }
        }
        
        if(plugin.getConfig().getBoolean("Entity-Damage.Players.Disable-Void-damage", true))
        {
            if(event.getCause().equals(DamageCause.VOID)) {
                if (entity instanceof Player) {
                    if(plugin.getConfig().getBoolean("Entity-Damage.Enable-permissions", true)) {
                        Player player = (Player)entity;
                        if(plugin.hasPermission(player, "iSafe.canceldamage.void")) {
                            event.setDamage(0);
                            event.setCancelled(true);
                        }
                    } else {
                        event.setDamage(0);
                        event.setCancelled(true);
                    }
                }
            }
        }
        if(plugin.getConfig().getBoolean("Entity-Damage.Creatures.Disable-Void-damage", true))
        {
            if(event.getCause().equals(DamageCause.VOID)) {
                if (entity instanceof Creature || entity instanceof Animals) {
                    event.setDamage(0);
                    event.setCancelled(true);
                }
            }
        }
    }

    @EventHandler
    public void onFoodLevelChange(FoodLevelChangeEvent event) {
        if (event.isCancelled())
        {
            return;
        }
        
        if(plugin.getConfig().getBoolean("Player.Disable-Hunger", true))
        {
            if(event.getEntity() instanceof Player) {
                Player p = (Player)event.getEntity();
                if(plugin.hasPermission(p, "iSafe.disablehunger")) {
                    event.setCancelled(true);
                    event.setFoodLevel(20);
                }
            }
        }
    }

    @EventHandler(priority = EventPriority.LOWEST)
    public void onEntityDeath(EntityDeathEvent event) {
        if(plugin.getConfig().getBoolean("World.Disable-ExperienceOrbs-drop", true))
        {
            event.setDroppedExp(0);
        }
        LivingEntity entity = event.getEntity();
        
        if(plugin.getCreatureManager().getBoolean("Creatures.Death.Disable-drops-onDeath", true))
        {
            if(!(entity instanceof Player)) {
                event.getDrops().clear();
            }
        }
    }

    @EventHandler
    public void onSlimeSplit(SlimeSplitEvent event) {
        if (event.isCancelled())
        {
            return;
        }
        
        if(plugin.getCreatureManager().getBoolean("Creatures.Slime.Prevent-SlimeSplit", true))
        {
            event.setCancelled(true);
        }
    }

    @EventHandler(priority = EventPriority.NORMAL)
    public void onEntityTarget(EntityTargetEvent event) {
        if (event.isCancelled())
        {
            return;
        }
        
        if(plugin.getCreatureManager().getBoolean("Creatures.CreatureTarget.Disable-closest_player-target", true))
        {
            if(event.getReason() == TargetReason.CLOSEST_PLAYER) 
            {
                if(event.getEntity() instanceof Player) {
                    Player target = (Player)event.getTarget();
                    if(plugin.hasPermission(target, "iSafe.canceltarget.closestplayer")) {
                        event.setCancelled(true);
                    }
                }
            }
        }
        if(plugin.getCreatureManager().getBoolean("Creatures.CreatureTarget.Disable-custom-target", true))
        {
            if(event.getReason() == TargetReason.CUSTOM) 
            {
                if(event.getEntity() instanceof Player) {
                    Player target = (Player)event.getTarget();
                    if(plugin.hasPermission(target, "iSafe.canceltarget.custom")) {
                        event.setCancelled(true);
                    }
                }
            }
        }
        if(plugin.getCreatureManager().getBoolean("Creatures.CreatureTarget.Disable-forgot_target-target", true))
        {
            if(event.getReason() == TargetReason.FORGOT_TARGET) 
            {
                if(event.getEntity() instanceof Player) {
                    Player target = (Player)event.getTarget();
                    if(plugin.hasPermission(target, "iSafe.canceltarget.forgot")) {
                        event.setCancelled(true);
                    }
                }
            }
        }
        if(plugin.getCreatureManager().getBoolean("Creatures.CreatureTarget.Disable-owner_attacked_target-target", true))
        {
            if(event.getReason() == TargetReason.OWNER_ATTACKED_TARGET) 
            {
                if(event.getEntity() instanceof Player) {
                    Player target = (Player)event.getTarget();
                    if(plugin.hasPermission(target, "iSafe.canceltarget.ownerattacked")) {
                        event.setCancelled(true);
                    }
                }
            }
        }
        if(plugin.getCreatureManager().getBoolean("Creatures.CreatureTarget.Disable-pig_zombie_target-target", true))
        {
            if(event.getReason() == TargetReason.PIG_ZOMBIE_TARGET) 
            {
                if(event.getEntity() instanceof Player) {
                    Player target = (Player)event.getTarget();
                    if(plugin.hasPermission(target, "iSafe.canceltarget.pigzombie")) {
                        event.setCancelled(true);
                    }
                }
            }
        }
        if(plugin.getCreatureManager().getBoolean("Creatures.CreatureTarget.Disable-random_target-target", true))
        {
            if(event.getReason() == TargetReason.RANDOM_TARGET) 
            {
                if(event.getEntity() instanceof Player) {
                    Player target = (Player)event.getTarget();
                    if(plugin.hasPermission(target, "iSafe.canceltarget.random")) {
                        event.setCancelled(true);
                    }
                }
            }
        }
        if(plugin.getCreatureManager().getBoolean("Creatures.CreatureTarget.Disable-target_attacked_entity-target", true))
        {
            if(event.getReason() == TargetReason.TARGET_ATTACKED_ENTITY) 
            {
                if(event.getEntity() instanceof Player) {
                    Player target = (Player)event.getTarget();
                    if(plugin.hasPermission(target, "iSafe.canceltarget.targetattackedentity")) {
                        event.setCancelled(true);
                    }
                }
            }
        }
        if(plugin.getCreatureManager().getBoolean("Creatures.CreatureTarget.Disable-target_attacked_owner-target", true))
        {
            if(event.getReason() == TargetReason.TARGET_ATTACKED_OWNER) 
            {
                if(event.getEntity() instanceof Player) {
                    Player target = (Player)event.getTarget();
                    if(plugin.hasPermission(target, "iSafe.canceltarget.targetattackedowner")) {
                        event.setCancelled(true);
                    }
                }
            }
        }
        if(plugin.getCreatureManager().getBoolean("Creatures.CreatureTarget.Disable-target_died-target", true))
        {
            if(event.getReason() == TargetReason.TARGET_DIED) 
            {
                if(event.getEntity() instanceof Player) {
                    Player target = (Player)event.getTarget();
                    if(plugin.hasPermission(target, "iSafe.canceltarget.targetdied")) {
                        event.setCancelled(true);
                    }
                }
            }
        }
    }
    
    @EventHandler(priority = EventPriority.NORMAL)
    public void manageCropTrampling(EntityInteractEvent event) {
        if (event.isCancelled())
        {
            return;
        }
        Entity entity = event.getEntity();
        
        if(plugin.getCreatureManager().getBoolean("Creatures.Prevent-cropTrampling", true))
        {
            if(entity instanceof LivingEntity) {
                if(entity instanceof Creature && !(entity instanceof Player)) {
                    event.setCancelled(true);
                }
            }
        }
        if(plugin.getCreatureManager().getBoolean("Player.Prevent-cropTrampling", true))
        {
            if(entity instanceof LivingEntity) {
                if(entity instanceof Player && !(entity instanceof Creature)) {
                    Player p = (Player)entity;
                    if(!(plugin.hasPermission(p, "iSafe.bypass.croptrampling"))) {
                        event.setCancelled(true);
                    }
                }
            }
        }
    }

    @EventHandler(priority = EventPriority.NORMAL)
    public void onPigZap(PigZapEvent event) {
        if (event.isCancelled())
        {
            return;
        }
        
        if(plugin.getCreatureManager().getBoolean("Creatures.Pig.Prevent-PigZap", true))
        {
            event.setCancelled(true);
        }
    }

    @EventHandler(priority = EventPriority.NORMAL)
    public void onEntityTame(EntityTameEvent event) {
        if (event.isCancelled())
        {
            return;
        }
        LivingEntity entity = event.getEntity();
        
        if(plugin.getCreatureManager().getBoolean("Creatures.Tame.Prevent-taming", true))
        {
            event.setCancelled(true);
        }
        
        if(plugin.getCreatureManager().getBoolean("Creatures.Tame.Prevent-taming-for.Wolf", true)) {
            if(entity instanceof Wolf) {
                event.setCancelled(true);
            }
        }
    }

    @EventHandler(priority = EventPriority.NORMAL)
    public void onItemSpawn(ItemSpawnEvent event) {
        if (event.isCancelled())
        {
            return;
        }
        
        if(plugin.getConfig().getBoolean("World.Prevent-items/objects-to-spawn-into-the-world", true))
        {
            event.setCancelled(true);
            event.getEntity().remove();
        }
        
        if(plugin.getConfig().getBoolean("World.Prevent-items/objects-spawning-inside-vehicles", true))
        {
            if(event.getEntity().isInsideVehicle()) {
                event.getEntity().eject();
                event.getEntity().remove();
            }
        }
    }

    @EventHandler(priority = EventPriority.NORMAL)
    public void onCreeperPower(CreeperPowerEvent event) {
        if (event.isCancelled())
        {
            return;
        }
        
        if(plugin.getCreatureManager().getBoolean("Creatures.PoweredCreepers.Prevent-PowerCause.Lightning", true))
        {
            if (event.getCause() == PowerCause.LIGHTNING) {
                event.setCancelled(true);
            }
        }
        if(plugin.getCreatureManager().getBoolean("Creatures.PoweredCreepers.Prevent-PowerCause.Set-Off", true))
        {
            if (event.getCause() == PowerCause.SET_OFF) {
                event.setCancelled(true);
            }
        }
        if(plugin.getCreatureManager().getBoolean("Creatures.PoweredCreepers.Prevent-PowerCause.Set-On", true))
        {
            if (event.getCause() == PowerCause.SET_ON) {
                event.setCancelled(true);
            }
        }
    }

    @EventHandler(priority = EventPriority.NORMAL)
    public void onEntityCombust(EntityCombustEvent event) {
        if (event.isCancelled())
        {
            return;
        }
        Entity entity = event.getEntity();
        
        if(plugin.getCreatureManager().getBoolean("Creatures.Combusting.Disable-for-allCreatures", true))
        {
            event.setDuration(0);
            event.setCancelled(true);
        }
        
        if(plugin.getCreatureManager().getBoolean("Creatures.Combusting.Disable-for.Blaze", true)) 
        {
            if(entity instanceof Blaze) {
                event.setDuration(0);
                event.setCancelled(true);
            }
        }
        
        if(plugin.getCreatureManager().getBoolean("Creatures.Combusting.Disable-for.CaveSpider", true)) 
        {
            if(entity instanceof CaveSpider) {
                event.setDuration(0);
                event.setCancelled(true);
            }
        }
        
        if(plugin.getCreatureManager().getBoolean("Creatures.Combusting.Disable-for.Chicken", true)) 
        {
            if(entity instanceof Chicken) {
                event.setDuration(0);
                event.setCancelled(true);
            }
        }
        
        if(plugin.getCreatureManager().getBoolean("Creatures.Combusting.Disable-for.Cow", true)) 
        {
            if(entity instanceof Cow) {
                event.setDuration(0);
                event.setCancelled(true);
            }
        }
        
        if(plugin.getCreatureManager().getBoolean("Creatures.Combusting.Disable-for.Creeper", true)) 
        {
            if(entity instanceof Creeper) {
                event.setDuration(0);
                event.setCancelled(true);
            }
        }
        
        if(plugin.getCreatureManager().getBoolean("Creatures.Combusting.Disable-for.EnderDragon", true)) 
        {
            if(entity instanceof EnderDragon) {
                event.setDuration(0);
                event.setCancelled(true);
            }
        }
        
        if(plugin.getCreatureManager().getBoolean("Creatures.Combusting.Disable-for.Enderman", true)) 
        {
            if(entity instanceof Enderman) {
                event.setDuration(0);
                event.setCancelled(true);
            }
        }
        
        if(plugin.getCreatureManager().getBoolean("Creatures.Combusting.Disable-for.Ghast", true)) 
        {
            if(entity instanceof Ghast) {
                event.setDuration(0);
                event.setCancelled(true);
            }
        }
        
        if(plugin.getCreatureManager().getBoolean("Creatures.Combusting.Disable-for.Giant", true)) 
        {
            if(entity instanceof Giant) {
                event.setDuration(0);
                event.setCancelled(true);
            }
        }
        
        if(plugin.getCreatureManager().getBoolean("Creatures.Combusting.Disable-for.Golem", true)) 
        {
            if(entity instanceof Golem) {
                event.setDuration(0);
                event.setCancelled(true);
            }
        }
        
        if(plugin.getCreatureManager().getBoolean("Creatures.Combusting.Disable-for.IronGolem", true)) 
        {
            if(entity instanceof IronGolem) {
                event.setDuration(0);
                event.setCancelled(true);
            }
        }
        
        if(plugin.getCreatureManager().getBoolean("Creatures.Combusting.Disable-for.MagmaCube", true)) 
        {
            if(entity instanceof MagmaCube) {
                event.setDuration(0);
                event.setCancelled(true);
            }
        }
        
        if(plugin.getCreatureManager().getBoolean("Creatures.Combusting.Disable-for.MushroomCow", true)) 
        {
            if(entity instanceof MushroomCow) {
                event.setDuration(0);
                event.setCancelled(true);
            }
        }
        
        if(plugin.getCreatureManager().getBoolean("Creatures.Combusting.Disable-for.Ocelot", true)) 
        {
            if(entity instanceof Ocelot) {
                event.setDuration(0);
                event.setCancelled(true);
            }
        }
        
        if(plugin.getCreatureManager().getBoolean("Creatures.Combusting.Disable-for.Pig", true)) 
        {
            if(entity instanceof Pig) {
                event.setDuration(0);
                event.setCancelled(true);
            }
        }
        
        if(plugin.getCreatureManager().getBoolean("Creatures.Combusting.Disable-for.PigZombie", true)) 
        {
            if(entity instanceof PigZombie) {
                event.setDuration(0);
                event.setCancelled(true);
            }
        }
        
        if(plugin.getCreatureManager().getBoolean("Creatures.Combusting.Disable-for.Sheep", true)) 
        {
            if(entity instanceof Sheep) {
                event.setDuration(0);
                event.setCancelled(true);
            }
        }
        
        if(plugin.getCreatureManager().getBoolean("Creatures.Combusting.Disable-for.Silverfish", true)) 
        {
            if(entity instanceof Silverfish) {
                event.setDuration(0);
                event.setCancelled(true);
            }
        }
        
        if(plugin.getCreatureManager().getBoolean("Creatures.Combusting.Disable-for.Skeleton", true)) 
        {
            if(entity instanceof Skeleton) {
                event.setDuration(0);
                event.setCancelled(true);
            }
        }
        
        if(plugin.getCreatureManager().getBoolean("Creatures.Combusting.Disable-for.Slime", true)) 
        {
            if(entity instanceof Slime) {
                event.setDuration(0);
                event.setCancelled(true);
            }
        }
        
        if(plugin.getCreatureManager().getBoolean("Creatures.Combusting.Disable-for.Snowman", true)) 
        {
            if(entity instanceof Snowman) {
                event.setDuration(0);
                event.setCancelled(true);
            }
        }
        
        if(plugin.getCreatureManager().getBoolean("Creatures.Combusting.Disable-for.Spider", true)) 
        {
            if(entity instanceof Spider) {
                event.setDuration(0);
                event.setCancelled(true);
            }
        }
        
        if(plugin.getCreatureManager().getBoolean("Creatures.Combusting.Disable-for.Squid", true)) 
        {
            if(entity instanceof Squid) {
                event.setDuration(0);
                event.setCancelled(true);
            }
        }
        
        if(plugin.getCreatureManager().getBoolean("Creatures.Combusting.Disable-for.Villager", true)) 
        {
            if(entity instanceof Villager) {
                event.setDuration(0);
                event.setCancelled(true);
            }
        }
        
        if(plugin.getCreatureManager().getBoolean("Creatures.Combusting.Disable-for.Wolf", true)) 
        {
            if(entity instanceof Wolf) {
                event.setDuration(0);
                event.setCancelled(true);
            }
        }
        
        if(plugin.getCreatureManager().getBoolean("Creatures.Combusting.Disable-for.Zombie", true)) 
        {
            if(entity instanceof Zombie) {
                event.setDuration(0);
                event.setCancelled(true);
            }
        }
    }

    @EventHandler(priority = EventPriority.NORMAL)
    public void onEntityRegainHealth(EntityRegainHealthEvent event) {
        if (event.isCancelled())
        {
            return;
        }
        
        if(plugin.getConfig().getBoolean("Entity/Player.Completely-Prevent.Health-Regeneration", true))
        {
            event.setCancelled(true);
        }
        if(plugin.getConfig().getBoolean("Entity/Player.Prevent.Custom-Health-Regeneration", true))
        {
            if (event.getRegainReason() == RegainReason.CUSTOM) 
            {
                event.setCancelled(true);
            }
        }
        if(plugin.getConfig().getBoolean("Entity/Player.Prevent.Eating-Health-Regeneration", true))
        {
            if (event.getRegainReason() == RegainReason.EATING) 
            {
                event.setCancelled(true);
            }
        }
        if(plugin.getConfig().getBoolean("Entity/Player.Prevent.Regen-Health-Regeneration", true))
        {
            if (event.getRegainReason() == RegainReason.REGEN) 
            {
                event.setCancelled(true);
            }
        }
        if(plugin.getConfig().getBoolean("Entity/Player.Prevent.Satiated-Health-Regeneration", true))
        {
            if (event.getRegainReason() == RegainReason.SATIATED) 
            {
                event.setCancelled(true);
            }
        }
        if(plugin.getConfig().getBoolean("Entity/Player.Prevent.Magic-Health-Regeneration", true))
        {
            if (event.getRegainReason() == RegainReason.MAGIC) 
            {
                event.setCancelled(true);
            }
        }
        if(plugin.getConfig().getBoolean("Entity/Player.Prevent.MagicRegen-Health-Regeneration", true))
        {
            if (event.getRegainReason() == RegainReason.MAGIC_REGEN) 
            {
                event.setCancelled(true);
            }
        }
    }
    
    @EventHandler
    public void SheepDyeWool(SheepDyeWoolEvent event) {
        if (event.isCancelled())
        {
            return;
        }
        
        if (plugin.getCreatureManager().getBoolean("Creatures.SheepDyeWool.Completely-Prevent-SheepDyeWool", true))
        {
            event.setCancelled(true);
        }    
        
        if (plugin.getCreatureManager().getBoolean("Creatures.SheepDyeWool.Prevent-SheepDyeWool-Color.Black", true))
        {
            if (event.getColor() == DyeColor.BLACK) 
            {
                event.setCancelled(true);
            }
        }
        
        if (plugin.getCreatureManager().getBoolean("Creatures.SheepDyeWool.Prevent-SheepDyeWool-Color.Blue", true))
        {
            if (event.getColor() == DyeColor.BLUE) 
            {
                event.setCancelled(true);
            }
        }
        
        if (plugin.getCreatureManager().getBoolean("Creatures.SheepDyeWool.Prevent-SheepDyeWool-Color.Brown", true))
        {
            if (event.getColor() == DyeColor.BROWN) 
            {
                event.setCancelled(true);
            }
        }
        
        if (plugin.getCreatureManager().getBoolean("Creatures.SheepDyeWool.Prevent-SheepDyeWool-Color.Cyan", true))
        {
            if (event.getColor() == DyeColor.CYAN) 
            {
                event.setCancelled(true);
            }
        }
        
        if (plugin.getCreatureManager().getBoolean("Creatures.SheepDyeWool.Prevent-SheepDyeWool-Color.Gray", true))
        {
            if (event.getColor() == DyeColor.GRAY) 
            {
                event.setCancelled(true);
            }
        }
        
        if (plugin.getCreatureManager().getBoolean("Creatures.SheepDyeWool.Prevent-SheepDyeWool-Color.Green", true))
        {
            if (event.getColor() == DyeColor.GREEN) 
            {
                event.setCancelled(true);
            }
        }
        
        if (plugin.getCreatureManager().getBoolean("Creatures.SheepDyeWool.Prevent-SheepDyeWool-Color.Light_Blue", true))
        {
            if (event.getColor() == DyeColor.LIGHT_BLUE) 
            {
                event.setCancelled(true);
            }
        }
        
        if (plugin.getCreatureManager().getBoolean("Creatures.SheepDyeWool.Prevent-SheepDyeWool-Color.Lime", true))
        {
            if (event.getColor() == DyeColor.LIME) 
            {
                event.setCancelled(true);
            }
        }
        
        if (plugin.getCreatureManager().getBoolean("Creatures.SheepDyeWool.Prevent-SheepDyeWool-Color.Magenta", true))
        {
            if (event.getColor() == DyeColor.MAGENTA) 
            {
                event.setCancelled(true);
            }
        }
        
        if (plugin.getCreatureManager().getBoolean("Creatures.SheepDyeWool.Prevent-SheepDyeWool-Color.Orange", true))
        {
            if (event.getColor() == DyeColor.ORANGE) 
            {
                event.setCancelled(true);
            }
        }
        
        if (plugin.getCreatureManager().getBoolean("Creatures.SheepDyeWool.Prevent-SheepDyeWool-Color.Pink", true))
        {
            if (event.getColor() == DyeColor.PINK) 
            {
                event.setCancelled(true);
            }
        }
        
        if (plugin.getCreatureManager().getBoolean("Creatures.SheepDyeWool.Prevent-SheepDyeWool-Color.Purple", true))
        {
            if (event.getColor() == DyeColor.PURPLE) 
            {
                event.setCancelled(true);
            }
        }
        
        if (plugin.getCreatureManager().getBoolean("Creatures.SheepDyeWool.Prevent-SheepDyeWool-Color.Red", true))
        {
            if (event.getColor() == DyeColor.RED) 
            {
                event.setCancelled(true);
            }
        }
        
        if (plugin.getCreatureManager().getBoolean("Creatures.SheepDyeWool.Prevent-SheepDyeWool-Color.Silver", true))
        {
            if (event.getColor() == DyeColor.SILVER) 
            {
                event.setCancelled(true);
            }
        }
        
        if (plugin.getCreatureManager().getBoolean("Creatures.SheepDyeWool.Prevent-SheepDyeWool-Color.White", true))
        {
            if (event.getColor() == DyeColor.WHITE) 
            {
                event.setCancelled(true);
            }
        }
        
        if (plugin.getCreatureManager().getBoolean("Creatures.SheepDyeWool.Prevent-SheepDyeWool-Color.Yellow", true))
        {
            if (event.getColor() == DyeColor.YELLOW) 
            {
                event.setCancelled(true);
            }
        }
    }
    
    @EventHandler
    public void EntityBreakDoor(EntityBreakDoorEvent event) {
        if(event.isCancelled()) 
        {
            return;
        }
        
        if(plugin.getCreatureManager().getBoolean("Creatures.DoorBreaking-PreventFor-zombies", true)) {
            event.setCancelled(true);
        }
    }
}
