package com.s4lpicon.blockShotRoulette.event.round;

import com.s4lpicon.blockShotRoulette.model.BlockShotGame;
import com.s4lpicon.blockShotRoulette.model.BlockShotPlayer;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;
import org.jetbrains.annotations.NotNull;

public class BlockShotRoundEndEvent extends Event {

    private static final HandlerList HANDLERS = new HandlerList();

    private final BlockShotGame blockShotGame;
    private final BlockShotPlayer roundWinner;

    public BlockShotRoundEndEvent(BlockShotGame blockShotGame, BlockShotPlayer roundWinner) {
        this.blockShotGame = blockShotGame;
        this.roundWinner = roundWinner;
    }

    public BlockShotPlayer getRoundWinner() {
        return roundWinner;
    }

    public BlockShotGame getBlockShotGame() {
        return blockShotGame;
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