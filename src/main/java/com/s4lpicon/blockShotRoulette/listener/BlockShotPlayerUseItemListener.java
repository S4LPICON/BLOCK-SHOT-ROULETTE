package com.s4lpicon.blockShotRoulette.listener;

import com.s4lpicon.blockShotRoulette.event.player.BlockShotPlayerUseItemEvent;
import com.s4lpicon.blockShotRoulette.item.BlockShotItemType;
import com.s4lpicon.blockShotRoulette.manager.ItemEffectManager;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;

import java.util.List;

public class BlockShotPlayerUseItemListener implements Listener {

    private final ItemEffectManager itemEffectManager;

    public BlockShotPlayerUseItemListener(ItemEffectManager itemEffectManager) {
        this.itemEffectManager = itemEffectManager;
    }

    @EventHandler
    public void onUseItem(BlockShotPlayerUseItemEvent event) {
        event.getPlayer().getPlayer().sendMessage("usaste el item: " + event.getItemType());
        List<BlockShotItemType> items = event.getPlayer().getItemManager().getItems();
        event.getPlayer().getPlayer().sendMessage("ahora tus items son: " + items);

        itemEffectManager.useItem(
                event.getPlayer(),
                event.getItemType()
        );




    }



}
