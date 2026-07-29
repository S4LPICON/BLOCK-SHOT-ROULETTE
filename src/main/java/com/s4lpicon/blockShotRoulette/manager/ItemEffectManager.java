package com.s4lpicon.blockShotRoulette.manager;

import com.s4lpicon.blockShotRoulette.item.BlockShotItemType;
import com.s4lpicon.blockShotRoulette.item.ShellType;
import com.s4lpicon.blockShotRoulette.model.BlockShotPlayer;

public class ItemEffectManager {

    public void useItem(BlockShotPlayer player, BlockShotItemType item) {

        switch (item) {

            case BEER -> useBeer(player);

            case HAND_SAW -> useHandSaw(player);

            case MAGNIFYING_GLASS -> useMagnifyingGlass(player);

            case HANDCUFFS -> useHandcuffs(player);

            default -> {
            }
        }
    }


    private void useBeer(BlockShotPlayer player) {
        // quitar una bala de la escopeta
        player.getBlockShotGame().getShotGun().removeFirst();
        //TODO: debe mostrar la bala
    }


    private void useHandSaw(BlockShotPlayer player) {
        player.getBlockShotGame()
                .getShotGun()
                .setSawedOff(true);
    }


    private void useMagnifyingGlass(BlockShotPlayer player) {
        ShellType shell = player.getBlockShotGame()
                .getShotGun()
                .peek();

        player.getPlayer()
                .sendMessage("La siguiente bala es: " + shell);
    }

    private void useHandcuffs(BlockShotPlayer player){

    }
}