package com.s4lpicon.blockShotRoulette.event.item;

import com.s4lpicon.blockShotRoulette.item.type.BlockShotItemType;
import com.s4lpicon.blockShotRoulette.model.BlockShotPlayer;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;
import org.jetbrains.annotations.NotNull;

public class BlockShotItemGiveFailedEvent extends Event {
    private static final HandlerList HANDLERS = new HandlerList();

    private final BlockShotPlayer player;
    private final BlockShotItemType item;

    public BlockShotItemGiveFailedEvent(BlockShotPlayer player, BlockShotItemType item) {
        this.player = player;
        this.item = item;
    }


    public BlockShotPlayer getPlayer() {
        return player;
    }

    public BlockShotItemType getItem() {
        return item;
    }

    @Override
    public @NotNull HandlerList getHandlers() {
        return HANDLERS;
    }

    public static HandlerList getHandlerList() {
        return HANDLERS;  //DONT DELETE
    }
}
