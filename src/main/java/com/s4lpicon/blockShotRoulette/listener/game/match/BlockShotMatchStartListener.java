package com.s4lpicon.blockShotRoulette.listener.game.match;

import com.s4lpicon.blockShotRoulette.event.match.BlockShotMatchStartEvent;
import com.s4lpicon.blockShotRoulette.model.BlockShotPlayer;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;

public class BlockShotMatchStartListener implements Listener {

    @EventHandler
    public void onMatchStart(BlockShotMatchStartEvent event){
        for (BlockShotPlayer player : event.getGame().getPlayers()){
            player.getPlayer().sendMessage("EL JUEGO VA A EMPEZAR!!!ñ");
        }
    }
}