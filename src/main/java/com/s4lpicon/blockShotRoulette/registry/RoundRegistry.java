package com.s4lpicon.blockShotRoulette.registry;

import com.s4lpicon.blockShotRoulette.registry.settings.RoundSettings;

import java.util.List;

public final class RoundRegistry {

    public static final List<RoundSettings> ROUNDS = List.of(
            new RoundSettings(2, 4, 3, 3),
            new RoundSettings(3, 3, 2, 3),
            new RoundSettings(3, 4, 3, 4)
    );

    private RoundRegistry() {}
}