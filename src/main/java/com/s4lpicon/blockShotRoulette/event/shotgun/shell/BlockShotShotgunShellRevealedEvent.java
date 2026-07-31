package com.s4lpicon.blockShotRoulette.event.shotgun.shell;

import com.s4lpicon.blockShotRoulette.item.ShotGun;
import com.s4lpicon.blockShotRoulette.model.BlockShotPlayer;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;
import org.jetbrains.annotations.NotNull;

public class BlockShotShotgunShellRevealedEvent extends Event {

    private static final HandlerList HANDLERS = new HandlerList();

    private final BlockShotPlayer player;
    private final ShotGun.ShellInfo shellInfo;

    public BlockShotShotgunShellRevealedEvent(BlockShotPlayer player, ShotGun.ShellInfo shellInfo) {
        this.player = player;
        this.shellInfo = shellInfo;
    }

    public BlockShotPlayer getPlayer() {
        return player;
    }

    public ShotGun.ShellInfo getShellInfo() {
        return shellInfo;
    }

    @Override
    public @NotNull HandlerList getHandlers() {
        return HANDLERS;
    }

    public static HandlerList getHandlerList() {
        return HANDLERS;  //DONT DELETE
    }
}
