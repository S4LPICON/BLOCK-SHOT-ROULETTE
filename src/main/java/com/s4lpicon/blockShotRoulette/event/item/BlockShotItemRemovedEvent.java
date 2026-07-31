package com.s4lpicon.blockShotRoulette.event.item;

import com.s4lpicon.blockShotRoulette.item.type.BlockShotItemType;
import com.s4lpicon.blockShotRoulette.model.BlockShotPlayer;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;
import org.jspecify.annotations.NonNull;

public class BlockShotItemRemovedEvent extends Event {

    private static final HandlerList HANDLERS = new HandlerList();

    private final BlockShotPlayer player;
    private final BlockShotItemType item;
    private final int slot;

    public BlockShotItemRemovedEvent(BlockShotPlayer player, BlockShotItemType item, int slot) {
        this.player = player;
        this.item = item;
        this.slot = slot;
    }

    public BlockShotPlayer getPlayer() {
        return player;
    }

    public BlockShotItemType getItem() {
        return item;
    }

    public int getSlot() {
        return slot;
    }

    @Override
    public @NonNull HandlerList getHandlers() {
        return HANDLERS;
    }

    public static HandlerList getHandlerList() {
        return HANDLERS;
    }
}