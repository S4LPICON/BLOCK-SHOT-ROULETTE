package com.s4lpicon.blockShotRoulette.event;

import com.s4lpicon.blockShotRoulette.model.BlockShotPlayer;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;
import org.jspecify.annotations.NonNull;

public class BlockShotPlayerShootEvent extends Event {

    private static final HandlerList HANDLERS = new HandlerList();

    private final BlockShotPlayer shooter;
    private final BlockShotPlayer target;

    public BlockShotPlayerShootEvent(BlockShotPlayer shooter, BlockShotPlayer target) {
        this.shooter = shooter;
        this.target = target;
    }

    public BlockShotPlayer getShooter() {
        return shooter;
    }

    public BlockShotPlayer getTarget(){
        return this.target;
    }
    @Override
    public @NonNull HandlerList getHandlers() {
        return HANDLERS;
    }

    public static HandlerList getHandlerList() {
        return HANDLERS;  //DONT DELETE
    }

}