package com.s4lpicon.blockShotRoulette.registry;


import com.s4lpicon.blockShotRoulette.registry.settings.MapSettings;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.World;

import java.util.List;
import java.util.Objects;


public class MapRegistry {


    private static World world = Objects.requireNonNull(world = Bukkit.getWorld("void"));

    public static final List<MapSettings> MAPS= List.of(

            new MapSettings(List.of(new Location(world, 3.4,-57.0,2.5),
                                    new Location(world, 7.5, -57.0, 6.5),
                                    new Location(world, 11.5, -57.0, 2.5),
                                    new Location(world, 7.5, -57.0,-1.5)))


            );


    private MapRegistry(){}
}
