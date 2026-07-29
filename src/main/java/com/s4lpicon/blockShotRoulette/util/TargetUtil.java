package com.s4lpicon.blockShotRoulette.util;

import org.bukkit.entity.ArmorStand;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.util.RayTraceResult;

import java.util.function.Predicate;

public final class TargetUtil {

    private TargetUtil() {}

    public static Entity getTarget(Player player, double distance, Predicate<Entity> filter) {

        RayTraceResult result = player.getWorld().rayTraceEntities(
                player.getEyeLocation(),
                player.getEyeLocation().getDirection(),
                distance,
                filter
        );

        return result == null ? null : result.getHitEntity();
    }

}