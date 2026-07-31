package com.s4lpicon.blockShotRoulette.manager;

import com.s4lpicon.blockShotRoulette.item.type.BlockShotItemType;
import com.s4lpicon.blockShotRoulette.item.type.ShellType;
import com.s4lpicon.blockShotRoulette.model.BlockShotPlayer;
import com.s4lpicon.blockShotRoulette.state.ShotGunState;

import java.util.Optional;

public class ItemEffectManager {

    public void useItem(BlockShotPlayer player, BlockShotItemType item) {

        switch (item) {

            case BEER -> useBeer(player);

            case HAND_SAW -> useHandSaw(player);

            case MAGNIFYING_GLASS -> useMagnifyingGlass(player);

            case HANDCUFFS -> useHandcuffs(player);

            case CIGARETTE_PACK -> useCigarettePack(player);

            default -> {
            }
        }
    }

    private void useCigarettePack(BlockShotPlayer player){
        player.addEnergy(1);
    }


    private void useBeer(BlockShotPlayer player) {
        Optional<ShellType> shellType = player.getBlockShotGame().getShotGun().ejectShell();
    }


    private void useHandSaw(BlockShotPlayer player) {
        player.getBlockShotGame()
                .getShotGun()
                .setState(ShotGunState.SAWED_OFF);
    }


    private void useMagnifyingGlass(BlockShotPlayer player) {
        Optional<ShellType> shell = player.getBlockShotGame()
                .getShotGun()
                .peek();

        player.getPlayer()
                .sendMessage("La siguiente bala es: " + shell);
    }

    private void useHandcuffs(BlockShotPlayer player){

    }
    //TODO FALTAN MUCHOS EFFECTOS POR MANEJAR
}