package com.s4lpicon.blockShotRoulette.listener.game.item;

import com.s4lpicon.blockShotRoulette.event.item.BlockShotItemRemovedEvent;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;

public class BlockShotItemRemovedListener implements Listener {

    @EventHandler
    public void onItemRemoved(BlockShotItemRemovedEvent event) {
        event.getPlayer().getPlayer().sendMessage("Has usado: " + event.getItem());
    }

}
