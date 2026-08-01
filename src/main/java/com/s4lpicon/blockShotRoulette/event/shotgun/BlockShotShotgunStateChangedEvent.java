package com.s4lpicon.blockShotRoulette.event.shotgun;

import com.s4lpicon.blockShotRoulette.item.ShotGun;
import com.s4lpicon.blockShotRoulette.model.BlockShotGame;
import com.s4lpicon.blockShotRoulette.state.ShotGunState;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;
import org.jetbrains.annotations.NotNull;

public class BlockShotShotgunStateChangedEvent extends Event {

    private static final HandlerList HANDLERS = new HandlerList();

    private final ShotGun shotgun;
    private final ShotGunState oldState;
    private final ShotGunState newState;
    private final BlockShotGame blockShotGame;

    public BlockShotShotgunStateChangedEvent(ShotGun shotgun, ShotGunState oldState, ShotGunState newState, BlockShotGame blockShotGame) {
        this.shotgun = shotgun;
        this.oldState = oldState;
        this.newState = newState;
        this.blockShotGame = blockShotGame;
    }

    public ShotGun getShotgun() {
        return shotgun;
    }

    public ShotGunState getOldState() {
        return oldState;
    }

    public ShotGunState getNewState() {
        return newState;
    }

    public BlockShotGame getBlockShotGame(){
        return this.blockShotGame;
    }

    @Override
    public @NotNull HandlerList getHandlers() {
        return HANDLERS;
    }

    public static HandlerList getHandlerList() {
        return HANDLERS;  //DONT DELETE
    }
}
