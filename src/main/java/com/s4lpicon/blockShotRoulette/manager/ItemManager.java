package com.s4lpicon.blockShotRoulette.manager;

import com.s4lpicon.blockShotRoulette.event.player.BlockShotPlayerUseItemEvent;
import com.s4lpicon.blockShotRoulette.item.BlockShotItemType;
import com.s4lpicon.blockShotRoulette.model.BlockShotPlayer;
import org.bukkit.Bukkit;

import java.util.Arrays;
import java.util.List;

public class ItemManager {

    private static final int MAX_ITEMS = 8;
    private final BlockShotPlayer blockShotPlayer;

    public ItemManager(BlockShotPlayer blockShotPlayer) {
        this.blockShotPlayer = blockShotPlayer;
    }


    // Dar un ítem al primer slot libre
    public boolean giveItem(BlockShotItemType item) {

        BlockShotItemType[] items = blockShotPlayer.getItems();

        for (int i = 0; i < items.length; i++) {

            if (items[i] == null) {
                items[i] = item;
                return true;
            }
        }

        return false; // inventario lleno
    }


    // Usar un ítem
    public void useItem(int slot) {

        if (slot < 0 || slot >= MAX_ITEMS) {
            return;
        }

        BlockShotItemType item = blockShotPlayer.getItems()[slot];

        if (item == null) {
            return;
        }
        removeItem(slot);

        Bukkit.getPluginManager().callEvent(
                new BlockShotPlayerUseItemEvent(blockShotPlayer, item, slot)
        );
    }


    // Quitar un ítem
    public void removeItem(int slot) {

        if (slot < 0 || slot >= MAX_ITEMS) {
            return;
        }

        blockShotPlayer.getItems()[slot] = null;
    }


    // Obtener los ítems del jugador
    public List<BlockShotItemType> getItems() {

        return Arrays.asList(blockShotPlayer.getItems());
    }

    // Comprobar si tiene espacio
    public boolean hasFreeSlot() {

        for (BlockShotItemType item : blockShotPlayer.getItems()) {

            if (item == null) {
                return true;
            }
        }

        return false;
    }


    // Limpiar todos los ítems
    public void clear() {

        Arrays.fill(blockShotPlayer.getItems(), null);
    }
}