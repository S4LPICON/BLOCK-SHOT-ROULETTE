package com.s4lpicon.blockShotRoulette.util;

import com.s4lpicon.blockShotRoulette.item.BlockShotItemType;

import java.util.Random;

public class ItemPoolUtil {
    private static final Random RANDOM = new Random();

    private ItemPoolUtil() {
    }

    public static BlockShotItemType randomItem() {
        BlockShotItemType[] items = BlockShotItemType.values();

        return items[RANDOM.nextInt(items.length)];
    }
}
