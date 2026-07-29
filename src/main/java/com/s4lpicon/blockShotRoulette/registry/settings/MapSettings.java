package com.s4lpicon.blockShotRoulette.registry.settings;

import org.bukkit.Location;

import java.util.List;
import java.util.Map;

public class MapSettings {
    private final List<Location> sitLocations;
    private  Map<Integer, List<Location>> itemLocations;//ubicacion de cada item de cada jugador
    //ubicacion de la escopeta
    //ubicacion del cofre
    //ubicaion de la camara para ver la escopeta
    //ubicaion de la camara para ver todos los items

    public MapSettings(List<Location> sitLocations) {
        this.sitLocations = sitLocations;
    }
    public MapSettings(List<Location> sitLocations, Map<Integer, List<Location>> itemLocations) {
        this.sitLocations = sitLocations;
        this.itemLocations = itemLocations;
    }

    public List<Location> getSitLocations(){
        return this.sitLocations;
    }
}
