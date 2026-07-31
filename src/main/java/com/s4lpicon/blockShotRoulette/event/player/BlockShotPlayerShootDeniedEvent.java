package com.s4lpicon.blockShotRoulette.event.player;

import com.s4lpicon.blockShotRoulette.event.player.model.ShootDeniedReason;
import com.s4lpicon.blockShotRoulette.model.BlockShotPlayer;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;
import org.jspecify.annotations.NonNull;

public class BlockShotPlayerShootDeniedEvent extends Event {
    private static final HandlerList HANDLERS = new HandlerList();

    private final BlockShotPlayer shooter;
    private final ShootDeniedReason reason;

    public BlockShotPlayerShootDeniedEvent(BlockShotPlayer shooter, ShootDeniedReason reason) {
        this.shooter = shooter;
        this.reason = reason;
    }

    public BlockShotPlayer getShooter() {
        return shooter;
    }

    public ShootDeniedReason getReason() {
        return reason;
    }

    @Override
    public @NonNull HandlerList getHandlers() {
        return HANDLERS;
    }

    public static HandlerList getHandlerList() {
        return HANDLERS;  //DONT DELETE
    }
}
