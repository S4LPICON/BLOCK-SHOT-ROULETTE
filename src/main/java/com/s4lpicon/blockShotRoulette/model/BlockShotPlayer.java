package com.s4lpicon.blockShotRoulette.model;

import com.s4lpicon.blockShotRoulette.event.player.BlockShotPlayerDamageEvent;
import com.s4lpicon.blockShotRoulette.event.player.BlockShotPlayerDeathEvent;
import com.s4lpicon.blockShotRoulette.event.player.BlockShotPlayerHealEvent;
import com.s4lpicon.blockShotRoulette.event.player.model.DeathReason;
import com.s4lpicon.blockShotRoulette.item.type.BlockShotItemType;
import com.s4lpicon.blockShotRoulette.state.PlayerState;
import com.s4lpicon.blockShotRoulette.task.AimItemTask;
import com.s4lpicon.blockShotRoulette.task.AimPlayerTask;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

public class BlockShotPlayer {

    private final Player player;
    private int energy;
    private PlayerState playerState;
    private final BlockShotGame blockShotGame;
    private AimPlayerTask aimPlayerTask;
    private AimItemTask aimItemTask;

    private final BlockShotItemType[] items = new BlockShotItemType[8];

    public BlockShotPlayer(Player player, BlockShotGame blockShotGame){
        this.player = player;
        this.playerState = PlayerState.WAITING;
        this.blockShotGame = blockShotGame;
    }


    public Player getPlayer(){
        return this.player;
    }

    public void setEnergy(int energy){
        this.energy = energy;
    }


    public int getEnergy(){
        return this.energy;
    }

    public void addEnergy(int amount){

        this.energy += amount;

        Bukkit.getPluginManager().callEvent(
                new BlockShotPlayerHealEvent(
                        this,
                        amount
                )
        );
    }

    public void takeDamage(BlockShotPlayer damager, int amount){

        if(playerState == PlayerState.DEAD){
            return;
        }

        this.energy -= amount;

        Bukkit.getPluginManager().callEvent(
                new BlockShotPlayerDamageEvent(
                        damager,
                        this,
                        amount
                )
        );

        if(this.energy <= 0){
            die(damager);
        }
    }

    public void die(BlockShotPlayer killer) {
        this.playerState = PlayerState.DEAD;

        DeathReason reason = killer == this
                ? DeathReason.SELF_SHOT
                : DeathReason.PLAYER_SHOT;

        Bukkit.getPluginManager().callEvent(
                new BlockShotPlayerDeathEvent(
                        this,
                        killer,
                        reason
                )
        );
    }

    public PlayerState getPlayerState(){
        return this.playerState;
    }

    public BlockShotGame getBlockShotGame(){
        return this.blockShotGame;
    }

    public void setPlayerState(PlayerState playerState){
        this.playerState = playerState;
    }

    public void setAimTask(AimPlayerTask aimPlayerTask){
        this.aimPlayerTask = aimPlayerTask;
    }

    public void setAimItemTask(AimItemTask aimItemTask){
        this.aimItemTask = aimItemTask;
    }

    public AimPlayerTask getAimTask(){
        return this.aimPlayerTask;
    }

    public AimItemTask getAimItemTask(){
        return this.aimItemTask;
    }

    public BlockShotItemType[] getItems(){
        return this.items;
    }
}
