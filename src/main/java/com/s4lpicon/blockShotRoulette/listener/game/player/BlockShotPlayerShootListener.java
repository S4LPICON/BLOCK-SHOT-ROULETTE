package com.s4lpicon.blockShotRoulette.listener.game.player;

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
        BlockShotPlayer target = event.getTarget();

        shooter.getPlayer().sendMessage(
                Component.text("Disparaste a: " + target));

    }
}
