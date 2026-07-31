package com.s4lpicon.blockShotRoulette.event.match;

import com.s4lpicon.blockShotRoulette.model.BlockShotGame;
import com.s4lpicon.blockShotRoulette.model.BlockShotPlayer;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;
import org.jspecify.annotations.NonNull;

public class BlockShotMatchEndEvent extends Event {

    private static final HandlerList HANDLERS = new HandlerList();

    private final BlockShotPlayer winner;
    private final BlockShotGame game;

    public BlockShotMatchEndEvent(BlockShotPlayer winner, BlockShotGame game) {
        this.winner = winner;
        this.game = game;
    }

    public BlockShotPlayer getWinner(){
        return this.winner;
    }

    public BlockShotGame getGame(){
        return this.game;
    }

    @Override
    public @NonNull HandlerList getHandlers() {
        return HANDLERS;
    }

    public static HandlerList getHandlerList() {
        return HANDLERS;
    }
}