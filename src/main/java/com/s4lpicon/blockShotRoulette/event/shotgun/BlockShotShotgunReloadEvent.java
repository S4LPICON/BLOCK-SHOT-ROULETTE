package com.s4lpicon.blockShotRoulette.event.shotgun;

import com.s4lpicon.blockShotRoulette.item.ShotGun;
import com.s4lpicon.blockShotRoulette.model.BlockShotGame;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;
import org.jetbrains.annotations.NotNull;

public class BlockShotShotgunReloadEvent extends Event {
    private static final HandlerList HANDLERS = new HandlerList();

    private final ShotGun shotGun;
    private final BlockShotGame game;

    public BlockShotShotgunReloadEvent(ShotGun shotGun, BlockShotGame game) {
        this.shotGun = shotGun;
        this.game = game;
    }

    public BlockShotGame getGame() {
        return game;
    }

    public ShotGun getShotGun() {
        return shotGun;
    }

    @Override
    public @NotNull HandlerList getHandlers() {
        return HANDLERS;
    }

    public static HandlerList getHandlerList() {
        return HANDLERS;  //DONT DELETE
    }
}
//TODO [EVENT] falta realizar este evento