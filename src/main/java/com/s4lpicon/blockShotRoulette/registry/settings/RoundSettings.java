package com.s4lpicon.blockShotRoulette.registry.settings;

public class RoundSettings {

    private final int lives;
    private final int liveShells;
    private final int blankShells;
    private final int itemsPerPlayer;

    public RoundSettings(int lives, int liveShells, int blankShells, int itemsPerPlayer) {
        this.lives = lives;
        this.liveShells = liveShells;
        this.blankShells = blankShells;
        this.itemsPerPlayer = itemsPerPlayer;
    }

    public int getLives() {
        return lives;
    }

    public int getLiveShells() {
        return liveShells;
    }

    public int getBlankShells() {
        return blankShells;
    }

    public int getItemsPerPlayer(){
        return itemsPerPlayer;
    }
}