package com.s4lpicon.blockShotRoulette.event;

import com.s4lpicon.blockShotRoulette.item.BlockShotItemType;
import com.s4lpicon.blockShotRoulette.model.BlockShotPlayer;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;
import org.jetbrains.annotations.NotNull;

public class BlockShotPlayerUseItemEvent extends Event {


    private final BlockShotPlayer player;
    private final BlockShotItemType type;
    private final int slot;

    private static final HandlerList HANDLERS = new HandlerList();

    public BlockShotPlayerUseItemEvent(BlockShotPlayer player, BlockShotItemType type, int slot) {
        this.player = player;
        this.type = type;
        this.slot = slot;
    }

    public BlockShotItemType getItemType(){
        return this.type;
    }

    public BlockShotPlayer getPlayer(){
        return this.player;
    }

    public int getSlot(){
        return this.slot;
    }

    @Override
    public @NotNull HandlerList getHandlers() {
        return HANDLERS;
    }

    public static HandlerList getHandlerList() {
        return HANDLERS;  //DONT DELETE
    }
}
