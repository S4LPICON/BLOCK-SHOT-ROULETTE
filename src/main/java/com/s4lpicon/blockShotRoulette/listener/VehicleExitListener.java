package com.s4lpicon.blockShotRoulette.listener;

import org.bukkit.Bukkit;
import org.bukkit.entity.ArmorStand;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDismountEvent;
import org.bukkit.event.vehicle.VehicleExitEvent;

public class VehicleExitListener implements Listener {

    @EventHandler
    public void onDismount(EntityDismountEvent event) {

        if (!(event.getEntity() instanceof Player)) {
            return;
        }

        if (!(event.getDismounted() instanceof ArmorStand)) {
            return;
        }

        event.setCancelled(true);
    }
}
