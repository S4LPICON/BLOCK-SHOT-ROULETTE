package com.s4lpicon.blockShotRoulette.event.player;

import com.s4lpicon.blockShotRoulette.event.player.model.DamageReason;
import com.s4lpicon.blockShotRoulette.model.BlockShotPlayer;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;
import org.jetbrains.annotations.NotNull;

import java.util.Optional;

public class BlockShotPlayerDamageEvent extends Event {
    private static final HandlerList HANDLERS = new HandlerList();

    private BlockShotPlayer shooter;
    private final BlockShotPlayer victim;
    private final int damage;
    private final DamageReason reason;

    public BlockShotPlayerDamageEvent(BlockShotPlayer shooter, BlockShotPlayer victim, int damage) {
        this.shooter = shooter;
        this.victim = victim;
        this.damage = damage;
        this.reason = DamageReason.PLAYER_SHOT;
    }

    public BlockShotPlayerDamageEvent(BlockShotPlayer victim, int damage, DamageReason reason) {
        this.victim = victim;
        this.damage = damage;
        this.reason = reason;
    }

    public Optional<BlockShotPlayer> getShooter(){
        return Optional.ofNullable(this.shooter);
    }

    public BlockShotPlayer getVictim(){
        return this.victim;
    }

    public int getDamage(){
        return this.damage;
    }

    public Optional<DamageReason> getReason(){
        return Optional.ofNullable(this.reason);
    }

    @Override
    public @NotNull HandlerList getHandlers() {
        return HANDLERS;
    }

    public static HandlerList getHandlerList() {
        return HANDLERS;  //DONT DELETE
    }
}
