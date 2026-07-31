package com.s4lpicon.blockShotRoulette.listener.game.item;

import com.s4lpicon.blockShotRoulette.event.item.BlockShotItemGiveFailedEvent;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;

public class BlockShotItemGiveFailedListener implements Listener {

    @EventHandler
    public void onItemGiveFailed(BlockShotItemGiveFailedEvent event) {
        event.getPlayer().getPlayer().sendMessage("no pudiste recivir: " + event.getItem());
    }

}
