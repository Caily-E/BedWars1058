package com.andrei1058.bedwars.listeners;

import com.andrei1058.bedwars.BedWars;
import com.andrei1058.bedwars.api.arena.team.ITeam;
import com.andrei1058.bedwars.api.configuration.ConfigPath;
import com.andrei1058.bedwars.api.events.player.PlayerGeneratorCollectEvent;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.inventory.ItemStack;

import java.util.List;

public class GoldSplit implements Listener {

    boolean enabled = BedWars.config.getBoolean(ConfigPath.GENERAL_CONFIGURATION_ENABLE_NEW_GEN_SPLIT);
    double split = BedWars.config.getDouble(ConfigPath.GENERAL_CONFIGURATION_ENABLE_NEW_GEN_SPLIT_RADIUS);


    @EventHandler
    public void onGoldPick(PlayerGeneratorCollectEvent e) {
        if (!e.isCancelled() && e.getItemStack().getType() == Material.GOLD_INGOT && enabled) {
            Location location = e.getPlayer().getLocation();
            Player p = e.getPlayer();
            List<Entity> near2 = (List<Entity>) location.getWorld().getNearbyEntities(location, split, split, split);
            for (Entity entity : location.getWorld().getEntities()) {
                if (near2.contains(entity) &&
                        entity instanceof Player) {
                    Player plr = (Player)entity;
                    if (plr.getUniqueId() != p.getUniqueId()) {
                        ITeam pt = BedWars.getAPI().getArenaUtil().getArenaByPlayer(plr).getTeam(p);
                        ITeam rt = BedWars.getAPI().getArenaUtil().getArenaByPlayer(plr).getTeam(plr);
                        if (pt == rt) {
                            ItemStack gold = new ItemStack(e.getItemStack().getType());
                            gold.setAmount(e.getItemStack().getAmount());
                            plr.getInventory().addItem(new ItemStack[] { gold });
                            plr.playSound(plr.getLocation(), Sound.valueOf(BedWars.getForCurrentVersion("ITEM_PICKUP", "ENTITY_ITEM_PICKUP", "ENTITY_ITEM_PICKUP")), 0.8f, 1.0f);
                        }
                    }
                }
            }
        }
    }
}

