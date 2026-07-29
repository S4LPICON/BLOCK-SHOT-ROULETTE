package com.s4lpicon.blockShotRoulette.manager;

import com.s4lpicon.blockShotRoulette.model.BlockShotPlayer;

import java.util.List;

public class TurnManager {

    private final List<BlockShotPlayer> players;

    private int currentIndex;
    private BlockShotPlayer actualTurnPlayer;
    private Direction direction = Direction.CLOCKWISE;

    public TurnManager(List<BlockShotPlayer> players) {
        this.players = players;
        this.actualTurnPlayer = this.players.getFirst();
    }

    public void next() {
        currentIndex = direction == Direction.CLOCKWISE
                ? (currentIndex + 1) % players.size()
                : (currentIndex - 1 + players.size()) % players.size();

        this.actualTurnPlayer = players.get(currentIndex);
    }

    public boolean isCurrentPlayer(BlockShotPlayer player){
        return player == actualTurnPlayer;
    }

    public void invertDirection() {
        direction = direction.opposite();
    }

    public BlockShotPlayer getActualTurnPlayer(){
        return this.actualTurnPlayer;
    }
}