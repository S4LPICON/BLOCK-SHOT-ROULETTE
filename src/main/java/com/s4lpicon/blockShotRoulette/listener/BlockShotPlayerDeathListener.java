package com.s4lpicon.blockShotRoulette.listener;

import com.s4lpicon.blockShotRoulette.event.BlockShotPlayerDamageEvent;
import com.s4lpicon.blockShotRoulette.event.BlockShotPlayerDeathEvent;
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
