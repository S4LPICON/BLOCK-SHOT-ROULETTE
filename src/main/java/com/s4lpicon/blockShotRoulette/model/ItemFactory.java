package com.s4lpicon.blockShotRoulette.model;

import com.s4lpicon.blockShotRoulette.item.type.BlockShotItemType;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

public final class ItemFactory {

    private ItemFactory() {}

    public static ItemStack create(BlockShotItemType type) {
        ItemStack item = new ItemStack(Material.STICK);

        ItemMeta meta = item.getItemMeta();
        meta.setCustomModelData(type.getCustomModelData());
        item.setItemMeta(meta);

        return item;
    }
}