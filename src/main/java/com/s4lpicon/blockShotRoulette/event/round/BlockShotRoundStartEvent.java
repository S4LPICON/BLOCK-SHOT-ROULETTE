package com.s4lpicon.blockShotRoulette.event.round;

import com.s4lpicon.blockShotRoulette.model.BlockShotGame;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;
import org.jetbrains.annotations.NotNull;

public class BlockShotRoundStartEvent extends Event {

    private static final HandlerList HANDLERS = new HandlerList();

    private final BlockShotGame game;

    public BlockShotRoundStartEvent(BlockShotGame game) {
        this.game = game;
    }

    public BlockShotGame getGame(){
        return this.game;
    }

    @Override
    public @NotNull HandlerList getHandlers() {
        return HANDLERS;
    }

    public static HandlerList getHandlerList() {
        return HANDLERS;  //DONT DELETE
    }
}
