package com.andrei1058.bedwars.listeners;

import com.andrei1058.bedwars.BedWars;
import org.bukkit.Material;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerDropItemEvent;
import org.bukkit.metadata.FixedMetadataValue;
import org.bukkit.metadata.MetadataValue;
import org.bukkit.plugin.Plugin;

public class ThrownItems implements Listener {

    @EventHandler
    public void onthrow(PlayerDropItemEvent e) {
        if (e.getItemDrop().getItemStack().getType() == Material.IRON_INGOT) {
            e.getItemDrop().setMetadata("thrownitem", (MetadataValue) new FixedMetadataValue((Plugin) BedWars.plugin, "yes"));
        } else if (e.getItemDrop().getItemStack().getType() == Material.GOLD_INGOT) {
            e.getItemDrop().setMetadata("thrownitem", (MetadataValue) new FixedMetadataValue((Plugin) BedWars.plugin, "yes"));
        } else {
            return;
        }
    }
}
