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

public class IronSplit implements Listener {

    boolean enabled = BedWars.config.getBoolean(ConfigPath.GENERAL_CONFIGURATION_ENABLE_NEW_GEN_SPLIT);
    double split = BedWars.config.getDouble(ConfigPath.GENERAL_CONFIGURATION_ENABLE_NEW_GEN_SPLIT_RADIUS);

    @EventHandler
    public void onIronPickup(PlayerGeneratorCollectEvent e) {
        if (!e.isCancelled() && e.getItemStack().getType() == Material.IRON_INGOT && enabled) {
            Location pl = e.getPlayer().getLocation();
            Player p = e.getPlayer();
            List<Entity> nearbyEntities2 = (List<Entity>)pl.getWorld().getNearbyEntities(pl, split, split, split);
            for (Entity en : pl.getWorld().getEntities()) {
                if (nearbyEntities2.contains(en) &&
                        en instanceof Player) {
                    Player r = (Player)en;
                    if (r.getUniqueId() != p.getUniqueId()) {
                        ITeam pt = BedWars.getAPI().getArenaUtil().getArenaByPlayer(r).getTeam(p);
                        ITeam rt = BedWars.getAPI().getArenaUtil().getArenaByPlayer(r).getTeam(r);
                        if (pt == rt) {
                            ItemStack iron = new ItemStack(e.getItemStack().getType());
                            iron.setAmount(e.getItemStack().getAmount());
                            r.getInventory().addItem(new ItemStack[] { iron });
                            p.playSound(p.getLocation(), Sound.valueOf(BedWars.getForCurrentVersion("ITEM_PICKUP", "ENTITY_ITEM_PICKUP", "ENTITY_ITEM_PICKUP")), 0.6f, 1.3f);
                        }
                    }
                }
            }
        }
    }
}