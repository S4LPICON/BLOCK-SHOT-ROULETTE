package com.s4lpicon.blockShotRoulette.manager;

import com.s4lpicon.blockShotRoulette.event.item.BlockShotItemGiveFailedEvent;
import com.s4lpicon.blockShotRoulette.event.item.BlockShotItemGivenEvent;
import com.s4lpicon.blockShotRoulette.event.item.BlockShotItemRemovedEvent;
import com.s4lpicon.blockShotRoulette.event.item.BlockShotItemUsedEvent;
import com.s4lpicon.blockShotRoulette.event.player.BlockShotPlayerItemsClearedEvent;
import com.s4lpicon.blockShotRoulette.item.type.BlockShotItemType;
import com.s4lpicon.blockShotRoulette.item.type.ShellType;
import com.s4lpicon.blockShotRoulette.model.BlockShotPlayer;
import com.s4lpicon.blockShotRoulette.registry.settings.RoundSettings;
import org.bukkit.Bukkit;
import org.jetbrains.annotations.NotNull;

import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.concurrent.ThreadLocalRandom;

public class ItemManager {

    private static final int MAX_ITEMS = 8;
    private final ItemEffectManager itemEffectManager;

    public ItemManager(ItemEffectManager itemEffectManager) {
        this.itemEffectManager = itemEffectManager;
    }

    public void clearItemsFromAll(List<@NotNull BlockShotPlayer> players ){
        for (BlockShotPlayer player : players){
            clear(player);
        }
    }

    public void giveItemsToAll(List<@NotNull BlockShotPlayer> players , RoundSettings settings){
        for (BlockShotPlayer player : players){
            for (int i = 0; i < settings.getItemsPerPlayer(); i++){
                BlockShotItemType radomItem = BlockShotItemType.values()[ThreadLocalRandom.current().nextInt(BlockShotItemType.values().length)];
                giveItem(player, radomItem);
            }
        }
    }

    public void giveItem(BlockShotPlayer blockShotPlayer, BlockShotItemType item) {

        BlockShotItemType[] items = blockShotPlayer.getItems();

        for (int i = 0; i < items.length; i++) {

            if (items[i] == null) {
                items[i] = item;

                Bukkit.getPluginManager().callEvent(
                        new BlockShotItemGivenEvent(
                                blockShotPlayer,
                                item,
                                i
                        )
                );

                return;
            }
        }

        Bukkit.getPluginManager().callEvent(
                new BlockShotItemGiveFailedEvent(
                        blockShotPlayer,
                        item
                )
        );
    }


    // Usar un ítem
    public void useItem(BlockShotPlayer player, int slot) {

        if (slot < 0 || slot >= MAX_ITEMS) {
            return;
        }

        BlockShotItemType item = player.getItems()[slot];

        if (item == null) {
            return;
        }

        player.getItems()[slot] = null;

        itemEffectManager.useItem(player, item);

        Bukkit.getPluginManager().callEvent(
                new BlockShotItemUsedEvent(
                        player,
                        item,
                        slot
                )
        );
    }


    // Quitar un ítem
    public boolean removeItem(BlockShotPlayer player, int slot) {

        if (slot < 0 || slot >= MAX_ITEMS) {
            return false;
        }

        BlockShotItemType item = player.getItems()[slot];

        if (item == null) {
            return false;
        }

        player.getItems()[slot] = null;

        Bukkit.getPluginManager().callEvent(
                new BlockShotItemRemovedEvent(
                        player,
                        item,
                        slot
                )
        );

        return true;
    }


    // Obtener los ítems del jugador
    public List<BlockShotItemType> getItems(BlockShotPlayer blockShotPlayer) {

        return Arrays.asList(blockShotPlayer.getItems());
    }

    // Comprobar si tiene espacio
    public boolean hasFreeSlot(BlockShotPlayer blockShotPlayer) {

        for (BlockShotItemType item : blockShotPlayer.getItems()) {

            if (item == null) {
                return true;
            }
        }

        return false;
    }


    // Limpiar todos los ítems
    public void clear(BlockShotPlayer blockShotPlayer) {

        com.s4lpicon.blockShotRoulette.item.type.BlockShotItemType[] items = blockShotPlayer.getItems();

        List<com.s4lpicon.blockShotRoulette.item.type.BlockShotItemType> removedItems = Arrays.stream(items)
                .filter(Objects::nonNull)
                .toList();

        Arrays.fill(items, null);

        Bukkit.getPluginManager().callEvent(
                new BlockShotPlayerItemsClearedEvent(
                        blockShotPlayer,
                        removedItems
                )
        );
    }
}