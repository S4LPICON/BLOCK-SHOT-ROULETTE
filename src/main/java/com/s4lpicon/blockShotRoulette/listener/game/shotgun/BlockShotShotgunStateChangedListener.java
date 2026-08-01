package com.s4lpicon.blockShotRoulette.listener.game.shotgun;

import com.s4lpicon.blockShotRoulette.event.shotgun.BlockShotShotgunStateChangedEvent;
import com.s4lpicon.blockShotRoulette.model.BlockShotPlayer;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;

public class BlockShotShotgunStateChangedListener implements Listener {

    @EventHandler
    public void onShotgunStateCHanged(BlockShotShotgunStateChangedEvent event){
        for (BlockShotPlayer player : event.getBlockShotGame().getPlayers()){
            player.getPlayer().sendMessage("-------------------------------------");
            player.getPlayer().sendMessage("La escopeta estaba " + event.getOldState() + " y ahora va a estar " + event.getNewState());
            player.getPlayer().sendMessage("-------------------------------------");
        }

    }
}