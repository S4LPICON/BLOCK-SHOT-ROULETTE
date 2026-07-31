package com.s4lpicon.blockShotRoulette.listener.game.player;

import com.s4lpicon.blockShotRoulette.event.player.BlockShotPlayerDamageEvent;
import com.s4lpicon.blockShotRoulette.model.BlockShotPlayer;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;

public class BlockShotPlayerDamageListener implements Listener {

    @EventHandler
    public void onDamage(BlockShotPlayerDamageEvent event) {
        BlockShotPlayer victim = event.getVictim();
        if (victim == event.getShooter()){
            victim.getPlayer().sendMessage("Te has disparado tu mismo");
        }

        victim.getPlayer()
                .sendMessage("Has recibido: " + event.getDamage() + " de daño!");
    }

}
