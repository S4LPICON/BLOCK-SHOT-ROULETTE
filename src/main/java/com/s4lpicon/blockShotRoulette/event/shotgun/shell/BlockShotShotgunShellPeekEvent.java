package com.s4lpicon.blockShotRoulette.event.shotgun.shell;

import com.s4lpicon.blockShotRoulette.item.type.ShellType;
import com.s4lpicon.blockShotRoulette.model.BlockShotPlayer;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;
import org.jspecify.annotations.NonNull;

public class BlockShotShotgunShellPeekEvent extends Event {

    private static final HandlerList HANDLERS = new HandlerList();

    private final BlockShotPlayer player;

    private final ShellType shellType;

    public BlockShotShotgunShellPeekEvent(BlockShotPlayer player, ShellType shellType) {
        this.player = player;
        this.shellType = shellType;
    }

    public ShellType getShellType() {
        return shellType;
    }

    public BlockShotPlayer getPlayer() {
        return player;
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