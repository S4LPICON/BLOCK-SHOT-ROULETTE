package com.s4lpicon.blockShotRoulette.manager;

public enum Direction {
    CLOCKWISE,
    COUNTER_CLOCKWISE;

    public Direction opposite() {
        return switch (this) {
            case CLOCKWISE -> COUNTER_CLOCKWISE;
            case COUNTER_CLOCKWISE -> CLOCKWISE;
        };
    }
}