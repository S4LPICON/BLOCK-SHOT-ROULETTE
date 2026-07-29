package com.s4lpicon.blockShotRoulette.listener;

import com.s4lpicon.blockShotRoulette.event.BlockShotPlayerShootEvent;
import com.s4lpicon.blockShotRoulette.model.BlockShotPlayer;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;

public class BlockShotPlayerShootListener implements Listener {

    @EventHandler
    public void onShoot(BlockShotPlayerShootEvent event) {

        BlockShotPlayer shooter = event.getShooter();
        shooter.getBlockShotGame().handleShoot(shooter, event.getTarget());

    }
}
