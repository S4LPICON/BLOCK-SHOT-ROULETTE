package com.s4lpicon.blockShotRoulette.listener;

import com.s4lpicon.blockShotRoulette.event.player.BlockShotPlayerDamageEvent;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;

public class BlockShotPlayerDamageListener implements Listener {

    @EventHandler
    public void onDamage(BlockShotPlayerDamageEvent event) {
        event.getVictim()
                .getPlayer()
                .sendMessage("Has recibido: " + event.getDamage() + " de daño!");
    }

}
