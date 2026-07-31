package com.s4lpicon.blockShotRoulette.event.match;

import com.s4lpicon.blockShotRoulette.model.BlockShotGame;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;
import org.jspecify.annotations.NonNull;

public class BlockShotMatchStartEvent extends Event {

    private static final HandlerList HANDLERS = new HandlerList();

    private final BlockShotGame game;
    //en un futuro añadir settings de la partida

    public BlockShotMatchStartEvent(BlockShotGame game) {
        this.game = game;
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