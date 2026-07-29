package com.s4lpicon.blockShotRoulette.item;

public enum BlockShotItemType {

    ADRENALINE(1),
    BURNER_PHONE(2),
    BEER(3),
    CIGARETTE_PACK(4),
    EXPIRED_MEDICINE(5),
    HAND_SAW(6),
    HANDCUFFS(7),
    INVERTER(8),
    JAMMER(9),
    MAGNIFYING_GLASS(10);

    private final int customModelData;

    BlockShotItemType(int customModelData) {this.customModelData = customModelData;}

    public int getCustomModelData() {return customModelData;}
}