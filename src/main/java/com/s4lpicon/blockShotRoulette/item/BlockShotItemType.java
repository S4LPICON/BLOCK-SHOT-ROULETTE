package com.s4lpicon.blockShotRoulette.item;

import org.bukkit.Location;

@Deprecated(forRemoval = true)
public class BlockShotItemType {
    @Deprecated(forRemoval = true)
    private com.s4lpicon.blockShotRoulette.item.type.BlockShotItemType type;
    @Deprecated(forRemoval = true)
    private final Location location;

    @Deprecated(forRemoval = true)
    public BlockShotItemType(Location location){
        this.location = location;
    }

    @Deprecated(forRemoval = true)
    public Location getLocation(){return this.location;}

    @Deprecated(forRemoval = true)
    public void setItemDisplay(com.s4lpicon.blockShotRoulette.item.type.BlockShotItemType blockShotItemType) {

    }

    @Deprecated(forRemoval = true)
    public com.s4lpicon.blockShotRoulette.item.type.BlockShotItemType getType(){
        return this.type;
    }


}
