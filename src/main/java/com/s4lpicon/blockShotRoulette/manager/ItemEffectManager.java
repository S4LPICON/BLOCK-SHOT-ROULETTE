package com.s4lpicon.blockShotRoulette.manager;

import com.s4lpicon.blockShotRoulette.event.player.BlockShotPlayerDamageEvent;
import com.s4lpicon.blockShotRoulette.event.player.BlockShotPlayerHealEvent;
import com.s4lpicon.blockShotRoulette.event.player.model.DamageReason;
import com.s4lpicon.blockShotRoulette.event.shotgun.BlockShotShotgunStateChangedEvent;
import com.s4lpicon.blockShotRoulette.event.shotgun.shell.BlockShotShotgunShellPeekEvent;
import com.s4lpicon.blockShotRoulette.event.shotgun.shell.BlockShotShotgunShellRevealFailedEvent;
import com.s4lpicon.blockShotRoulette.event.shotgun.shell.BlockShotShotgunShellRevealedEvent;
import com.s4lpicon.blockShotRoulette.item.ShotGun;
import com.s4lpicon.blockShotRoulette.item.type.BlockShotItemType;
import com.s4lpicon.blockShotRoulette.item.type.ShellType;
import com.s4lpicon.blockShotRoulette.model.BlockShotPlayer;
import com.s4lpicon.blockShotRoulette.state.ShotGunState;
import org.bukkit.Bukkit;

import java.util.Optional;

public class ItemEffectManager {
    private static final double MEDICINE_HEAL_CHANCE = 0.40;
    private static final int MEDICINE_HEAL_AMOUNT = 2;
    private static final int MEDICINE_DAMAGE_AMOUNT = 1;
    private static final int CIGARETTE_HEAL_AMOUNT = 1;

    public void useItem(BlockShotPlayer player, BlockShotItemType item) {

        switch (item) {
            case ADRENALINE -> useAdrenaline(player);

            case BURNER_PHONE -> useBurnerPhone(player);

            case BEER -> useBeer(player);

            case CIGARETTE_PACK -> useCigarettePack(player);

            case EXPIRED_MEDICINE -> useExpiredMedicine(player);

            case HAND_SAW -> useHandSaw(player);

            case HANDCUFFS -> useHandcuffs(player);

            case INVERTER -> useInverter(player);

            case JAMMER -> useJammer(player);

            case MAGNIFYING_GLASS -> useMagnifyingGlass(player);

            default -> {
            }
        }
    }

    private void useAdrenaline(BlockShotPlayer player){ //TODO

    }

    private void useBurnerPhone(BlockShotPlayer player) {

        ShotGun shotGun = player.getBlockShotGame().getShotGun();

        Optional<ShotGun.ShellInfo> result = shotGun.getRandomFutureShell();

        if (result.isEmpty()) {

            Bukkit.getPluginManager().callEvent(
                    new BlockShotShotgunShellRevealFailedEvent(
                            player)
            );

            return;
        }

        Bukkit.getPluginManager().callEvent(
                new BlockShotShotgunShellRevealedEvent(
                        player,
                        result.get()
                )
        );
    }

    private void useBeer(BlockShotPlayer player) {
        Optional<ShellType> shellType = player.getBlockShotGame().getShotGun().ejectFirstShell();
    }

    private void useCigarettePack(BlockShotPlayer player){
        player.getBlockShotGame().getEventDispatcher().call(
                new BlockShotPlayerHealEvent(
                        player,
                        CIGARETTE_HEAL_AMOUNT
                )
        );
        player.addEnergy(CIGARETTE_HEAL_AMOUNT);
    }

    private void useExpiredMedicine(BlockShotPlayer player) {

        if (Math.random() < MEDICINE_HEAL_CHANCE) {
            player.addEnergy(MEDICINE_HEAL_AMOUNT);
            player.getBlockShotGame().getEventDispatcher().call(
                    new BlockShotPlayerHealEvent(
                            player,
                            MEDICINE_HEAL_AMOUNT
                    )
            );
        } else {
            player.takeDamage(MEDICINE_DAMAGE_AMOUNT);//no se si deberia llamar a un evento
            player.getBlockShotGame().getEventDispatcher().call(
                    new BlockShotPlayerDamageEvent(
                            player,
                            MEDICINE_DAMAGE_AMOUNT,
                            DamageReason.EXPIRED_MEDICINE
                    )
            );
        }
    }

    private void useHandSaw(BlockShotPlayer player) {

        ShotGun shotGun = player.getBlockShotGame().getShotGun();
        ShotGunState oldState = shotGun.getState();
        shotGun.setState(ShotGunState.SAWED_OFF);

        Bukkit.getPluginManager().callEvent(
                new BlockShotShotgunStateChangedEvent(
                        shotGun,
                        oldState,
                        ShotGunState.SAWED_OFF,
                        player.getBlockShotGame()
                )
        );
    }

    private void useHandcuffs(BlockShotPlayer player){ //no aun este es para modo de 2 jugadores

    }

    private void useInverter(BlockShotPlayer player){
        player.getBlockShotGame()
                .getShotGun()
                .invertNextShell();
    }

    private void useJammer(BlockShotPlayer player){ //TODO

    }

    private void useMagnifyingGlass(BlockShotPlayer player) {
        ShellType shell = player.getBlockShotGame()
                .getShotGun()
                .peek();

        player.getBlockShotGame().getEventDispatcher().call(
                new BlockShotShotgunShellPeekEvent(
                        player,
                        shell
                )
        );
    }


    //TODO FALTAN MUCHOS EFFECTOS POR MANEJAR
}