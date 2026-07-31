package com.s4lpicon.blockShotRoulette.listener.game.item;

import com.s4lpicon.blockShotRoulette.event.item.BlockShotItemUsedEvent;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;

import java.util.Arrays;

public class BlockShotItemUsedListener implements Listener {

    @EventHandler
    public void onItemUsed(BlockShotItemUsedEvent event) {
        event.getPlayer().getPlayer().sendMessage("usaste el item: " + event.getItemType());
        event.getPlayer().getPlayer().sendMessage("ahora tus items son: " + Arrays.toString(event.getPlayer().getItems()));
    }
}
