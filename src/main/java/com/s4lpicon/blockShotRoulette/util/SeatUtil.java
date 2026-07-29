package com.s4lpicon.blockShotRoulette.util;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.entity.ArmorStand;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;

public final class SeatUtil {

    private ArmorStand entity;

    private SeatUtil(){}

    public static void sit(JavaPlugin plugin, Player player, Location location) {

        ArmorStand seat = location.getWorld().spawn(location, ArmorStand.class, stand -> {
            stand.setInvisible(true);
            stand.setMarker(true);
            stand.setGravity(false);
            stand.setSmall(true);
        });

        Bukkit.getScheduler().runTask(plugin, () -> {
            seat.addPassenger(player);
        });

    }

    public void remove() {
        if (entity != null) {
            entity.remove();
        }
    }
}
