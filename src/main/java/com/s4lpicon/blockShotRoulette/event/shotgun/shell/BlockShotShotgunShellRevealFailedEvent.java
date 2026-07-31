package com.s4lpicon.blockShotRoulette.event.shotgun.shell;

import com.s4lpicon.blockShotRoulette.model.BlockShotPlayer;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;
import org.jetbrains.annotations.NotNull;

public class BlockShotShotgunShellRevealFailedEvent extends Event {

    private static final HandlerList HANDLERS = new HandlerList();

    private final BlockShotPlayer player;

    public BlockShotShotgunShellRevealFailedEvent(BlockShotPlayer player) {
        this.player = player;
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
