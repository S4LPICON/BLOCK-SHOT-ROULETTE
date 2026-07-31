package com.s4lpicon.blockShotRoulette.event.turn;

import com.s4lpicon.blockShotRoulette.model.BlockShotGame;
import com.s4lpicon.blockShotRoulette.model.BlockShotPlayer;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;
import org.jetbrains.annotations.NotNull;

public class BlockShotTurnEndEvent extends Event {

    private static final HandlerList HANDLERS = new HandlerList();

    private final BlockShotGame game;
    private final BlockShotPlayer player;

    public BlockShotTurnEndEvent(BlockShotGame game, BlockShotPlayer player) {
        this.game = game;
        this.player = player;
    }

    public BlockShotGame getGame() {
        return game;
    }

    public BlockShotPlayer getPlayer() {
        return player;
    }

    @Override
    public @NotNull HandlerList getHandlers() {
        return HANDLERS;
    }

    public static HandlerList getHandlerList() {
        return HANDLERS;
    }
}
