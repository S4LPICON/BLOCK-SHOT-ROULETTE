package com.s4lpicon.blockShotRoulette.event.player;

import com.s4lpicon.blockShotRoulette.item.type.BlockShotItemType;
import com.s4lpicon.blockShotRoulette.model.BlockShotPlayer;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;
import org.jspecify.annotations.NonNull;

import java.util.List;

public class BlockShotPlayerItemsClearedEvent extends Event {

    private static final HandlerList HANDLERS = new HandlerList();

    private final BlockShotPlayer player;
    private final List<BlockShotItemType> items;

    public BlockShotPlayerItemsClearedEvent(BlockShotPlayer player, List<BlockShotItemType> items) {
        this.player = player;
        this.items = items;
    }

    public BlockShotPlayer getPlayer() {
        return player;
    }

    public List<BlockShotItemType> getItems() {
        return items;
    }

    @Override
    public @NonNull HandlerList getHandlers() {
        return HANDLERS;
    }

    public static HandlerList getHandlerList() {
        return HANDLERS;  //DONT DELETE
    }
}
//TODO [EVENT] falta realizar este evento