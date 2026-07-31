package com.s4lpicon.blockShotRoulette.listener.game.player;

import com.s4lpicon.blockShotRoulette.event.player.BlockShotPlayerDeathEvent;
import org.bukkit.GameMode;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;

public class BlockShotPlayerDeathListener implements Listener {

    @EventHandler
    public void onDeath(BlockShotPlayerDeathEvent event) {
        event.getVictim()
                .getPlayer()
                .sendMessage("Te ha matado: " + event.getKiller().getPlayer().getName());
        event.getVictim().getPlayer().setGameMode(GameMode.SPECTATOR);

    }
}
