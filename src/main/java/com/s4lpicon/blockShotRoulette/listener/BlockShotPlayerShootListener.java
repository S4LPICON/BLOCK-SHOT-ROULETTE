package com.s4lpicon.blockShotRoulette.listener;

import com.s4lpicon.blockShotRoulette.event.player.BlockShotPlayerShootEvent;
import com.s4lpicon.blockShotRoulette.model.BlockShotPlayer;
import com.s4lpicon.blockShotRoulette.util.TaskUtil;
import net.kyori.adventure.text.Component;
import org.bukkit.Bukkit;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;

public class BlockShotPlayerShootListener implements Listener {

    @EventHandler
    public void onShoot(BlockShotPlayerShootEvent event) {
        BlockShotPlayer shooter = event.getShooter();
        if (shooter.getAimTask() == null) return;
        Bukkit.getServer().broadcast(Component.text("ESPEREN 4 SEGUNDOS QUE ACABAN DE DISPARAR, Disparo: " + event.getShooter().getPlayer().getName() + " a: " + event.getTarget().getPlayer().getName())) ;
        shooter.getAimTask().stop();
        shooter.setAimTask(null);
        TaskUtil.waitTicks(80, () -> {
            shooter.getBlockShotGame().handleShoot(shooter, event.getTarget());
        });

    }
}
