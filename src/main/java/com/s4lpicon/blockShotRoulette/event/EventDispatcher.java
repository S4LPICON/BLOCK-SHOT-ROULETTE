package com.s4lpicon.blockShotRoulette.event;

import org.bukkit.Bukkit;
import org.bukkit.event.Event;

public class EventDispatcher{

    public void call(Event event) {
        Bukkit.getPluginManager().callEvent(event);
    }
}