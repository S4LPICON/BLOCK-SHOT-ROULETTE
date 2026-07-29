package com.s4lpicon.blockShotRoulette.util;

import com.s4lpicon.blockShotRoulette.BlockShotRoulette;
import org.bukkit.scheduler.BukkitRunnable;

public final class TaskUtil {

    private TaskUtil(){}

    public static void waitTicks(long ticks, Runnable runnable) {

        new BukkitRunnable() {
            @Override
            public void run() {
                runnable.run();
            }
        }.runTaskLater(BlockShotRoulette.getInstance(), ticks);
    }
}