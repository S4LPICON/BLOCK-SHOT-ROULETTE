package com.s4lpicon.blockShotRoulette.event.player;

import com.s4lpicon.blockShotRoulette.model.BlockShotPlayer;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;
import org.jetbrains.annotations.NotNull;

public class BlockShotPlayerDeathEvent extends Event {

    private static final HandlerList HANDLERS = new HandlerList();

    private final BlockShotPlayer killer;
    private final BlockShotPlayer victim;


    public BlockShotPlayerDeathEvent(BlockShotPlayer killer, BlockShotPlayer victim) {
        this.killer = killer;
        this.victim = victim;
    }

    public BlockShotPlayer getKiller(){
        return this.killer;
    }

    public BlockShotPlayer getVictim(){
        return this.victim;
    }

    @Override
    public @NotNull HandlerList getHandlers() {
        return HANDLERS;
    }

    public static HandlerList getHandlerList() {
        return HANDLERS;  //DONT DELETE
    }
}
