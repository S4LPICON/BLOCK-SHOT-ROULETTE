package com.s4lpicon.blockShotRoulette.listener.game.player;


import com.s4lpicon.blockShotRoulette.event.player.BlockShotPlayerHealEvent;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;

public class BlockShotPlayerHealListener implements Listener {

    @EventHandler
    public void onHeal(BlockShotPlayerHealEvent event) {
        event.getPlayer().getPlayer().sendMessage("TE has curado: " + event.getAmount() + " de energia!");
        event.getPlayer().getPlayer().sendMessage("Ahora tue energia es de " + event.getPlayer().getEnergy());

    }
}