package com.s4lpicon.blockShotRoulette.event.round;

import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;
import org.jetbrains.annotations.NotNull;

public class BlockShotRoundStartEvent extends Event {

    private static final HandlerList HANDLERS = new HandlerList();

    //TODO [EVENT] falta realizar este evento


    @Override
    public @NotNull HandlerList getHandlers() {
        return HANDLERS;
    }

    public static HandlerList getHandlerList() {
        return HANDLERS;  //DONT DELETE
    }
}
