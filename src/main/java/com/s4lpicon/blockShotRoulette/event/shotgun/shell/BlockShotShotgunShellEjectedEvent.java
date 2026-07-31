package com.s4lpicon.blockShotRoulette.event.shotgun.shell;

import com.s4lpicon.blockShotRoulette.item.ShotGun;
import com.s4lpicon.blockShotRoulette.item.type.ShellType;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;
import org.jspecify.annotations.NonNull;

public class BlockShotShotgunShellEjectedEvent extends Event {

    private static final HandlerList HANDLERS = new HandlerList();

    private final ShotGun shotGun;
    private final ShellType shell;

    public BlockShotShotgunShellEjectedEvent(ShotGun shotGun, ShellType shell) {
        this.shotGun = shotGun;
        this.shell = shell;
    }

    public ShotGun getShotGun() {
        return shotGun;
    }

    public ShellType getShell() {
        return shell;
    }

    @Override
    public @NonNull HandlerList getHandlers() {
        return HANDLERS;
    }

    public static HandlerList getHandlerList() {
        return HANDLERS;  //DONT DELETE
    }
}