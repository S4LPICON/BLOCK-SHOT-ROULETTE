package com.s4lpicon.blockShotRoulette.manager;

import com.s4lpicon.blockShotRoulette.manager.model.Direction;
import com.s4lpicon.blockShotRoulette.model.BlockShotPlayer;
import com.s4lpicon.blockShotRoulette.state.PlayerState;

import java.util.List;

public class TurnManager {

    private final List<BlockShotPlayer> players;

    private int currentIndex = 0;
    private BlockShotPlayer actualTurnPlayer;

    private Direction direction = Direction.CLOCKWISE;

    public TurnManager(List<BlockShotPlayer> players) {
        if (players.isEmpty()) {
            throw new IllegalArgumentException("Cannot create TurnManager without players");
        }

        this.players = players;
        this.currentIndex = 0;

        // Validamos si el primero está vivo, si no, buscamos al primer sobreviviente
        if (players.getFirst().getPlayerState() != PlayerState.DEAD) {
            this.actualTurnPlayer = players.getFirst();
        } else {
            this.actualTurnPlayer = next(); // Esto encontrará al primer jugador vivo de forma segura
        }
    }

    private void nextIndex() {

        if (direction == Direction.CLOCKWISE) {
            currentIndex = (currentIndex + 1) % players.size();
        } else {
            currentIndex = (currentIndex - 1 + players.size()) % players.size();
        }

    }

    public BlockShotPlayer next() {

        int checkedPlayers = 0;

        while (checkedPlayers < players.size()) {

            nextIndex();

            BlockShotPlayer player = players.get(currentIndex);

            if (player.getPlayerState() != PlayerState.DEAD) {
                actualTurnPlayer = player;
                return player;
            }

            checkedPlayers++;
        }

        return null;
    }

    public boolean isCurrentPlayer(BlockShotPlayer player) {

        return player != null && player == actualTurnPlayer;

    }

    public BlockShotPlayer getActualTurnPlayer() {

        return actualTurnPlayer;

    }

    public void invertDirection() {

        direction = direction.opposite();

    }

}