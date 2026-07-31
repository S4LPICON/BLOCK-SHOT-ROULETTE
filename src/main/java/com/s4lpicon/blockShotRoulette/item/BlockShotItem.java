package com.s4lpicon.blockShotRoulette.item;

import com.s4lpicon.blockShotRoulette.item.type.BlockShotItemType;
import com.s4lpicon.blockShotRoulette.model.ItemFactory;
import org.bukkit.Location;
import org.bukkit.entity.ItemDisplay;
import org.bukkit.inventory.ItemStack;

public class BlockShotItem {

    private BlockShotItemType type;
    private final Location location;
    private ItemDisplay itemDisplay;

    public BlockShotItem(Location location){
        this.location = location;
    }


    public Location getLocation(){return this.location;}


    public void setItemDisplay(BlockShotItemType blockShotItemType) {
        ItemStack item = ItemFactory.create(blockShotItemType);

        this.itemDisplay = location.getWorld().spawn(location, ItemDisplay.class, entity -> {
            entity.setItemStack(item);
        });
    }


    public BlockShotItemType getType(){
        return this.type;
    }


}
