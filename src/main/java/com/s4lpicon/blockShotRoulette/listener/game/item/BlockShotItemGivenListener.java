package com.s4lpicon.blockShotRoulette.listener.game.item;

import com.s4lpicon.blockShotRoulette.event.item.BlockShotItemGivenEvent;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;

public class BlockShotItemGivenListener implements Listener {

    @EventHandler
    public void onItemGiven(BlockShotItemGivenEvent event) {
        event.getPlayer().getPlayer().sendMessage("Has recibido un: " + event.getItem());
    }


}
