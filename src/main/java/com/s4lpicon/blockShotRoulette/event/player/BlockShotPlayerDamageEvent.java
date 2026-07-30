package com.s4lpicon.blockShotRoulette.event.player;

import com.s4lpicon.blockShotRoulette.model.BlockShotPlayer;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;
import org.jetbrains.annotations.NotNull;

public class BlockShotPlayerDamageEvent extends Event {
    private static final HandlerList HANDLERS = new HandlerList();

    private final BlockShotPlayer shooter;
    private final BlockShotPlayer victim;
    private final int damage;

    public BlockShotPlayerDamageEvent(BlockShotPlayer shooter, BlockShotPlayer victim, int damage) {
        this.shooter = shooter;
        this.victim = victim;
        this.damage = damage;
    }

    public BlockShotPlayer getShooter(){
        return this.shooter;
    }

    public BlockShotPlayer getVictim(){
        return this.victim;
    }

    public int getDamage(){
        return this.damage;
    }

    @Override
    public @NotNull HandlerList getHandlers() {
        return HANDLERS;
    }

    public static HandlerList getHandlerList() {
        return HANDLERS;  //DONT DELETE
    }
}
