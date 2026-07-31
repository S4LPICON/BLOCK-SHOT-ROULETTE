package com.s4lpicon.blockShotRoulette.event.player;

import com.s4lpicon.blockShotRoulette.model.BlockShotPlayer;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;
import org.jetbrains.annotations.NotNull;

public class BlockShotPlayerHealEvent extends Event {

    private static final HandlerList HANDLERS = new HandlerList();

    private final BlockShotPlayer player;
    private final int amount;

    public BlockShotPlayerHealEvent(BlockShotPlayer player, int amount) {
        this.player = player;
        this.amount = amount;
    }

    public int getAmount() {
        return amount;
    }

    public BlockShotPlayer getPlayer() {
        return player;
    }

    @Override
    public @NotNull HandlerList getHandlers() {
        return HANDLERS;
    }

    public static HandlerList getHandlerList() {
        return HANDLERS;  //DONT DELETE
    }
}
