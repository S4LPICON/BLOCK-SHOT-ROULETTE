package com.s4lpicon.blockShotRoulette.listener.game.turn;

import com.s4lpicon.blockShotRoulette.event.turn.BlockShotTurnStartEvent;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;


public class BlockShotTurnStartListener implements Listener {
    @EventHandler
    public void onTurnStart(BlockShotTurnStartEvent event){
        event.getPlayer().getPlayer().sendMessage("Te toca a ti! elige a quien disparar!");
    }
}
