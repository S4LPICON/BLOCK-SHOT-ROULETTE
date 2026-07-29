package com.s4lpicon.blockShotRoulette.model;

import com.s4lpicon.blockShotRoulette.item.BlockShotItemType;
import com.s4lpicon.blockShotRoulette.manager.ItemManager;
import com.s4lpicon.blockShotRoulette.state.PlayerState;
import com.s4lpicon.blockShotRoulette.task.AimItemTask;
import com.s4lpicon.blockShotRoulette.task.AimTask;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.List;

public class BlockShotPlayer {

    private final Player player;
    private int energy;
    private PlayerState playerState;
    private final BlockShotGame blockShotGame;
    private AimTask aimTask;
    private AimItemTask aimItemTask;

    private final BlockShotItemType[] items = new BlockShotItemType[8];

    private final ItemManager itemManager = new ItemManager(this);

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

    public void addEnergy(int energy){
        this.energy += energy;
    }

    public void damage(int damage){
        this.energy -= damage;
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

    public void setAimTask(AimTask aimTask){
        this.aimTask = aimTask;
    }

    public void setAimItemTask(AimItemTask aimItemTask){
        this.aimItemTask = aimItemTask;
    }

    public AimTask getAimTask(){
        return this.aimTask;
    }

    public AimItemTask getAimItemTask(){
        return this.aimItemTask;
    }

    public BlockShotItemType[] getItems(){
        return this.items;
    }

    public ItemManager getItemManager(){
        return this.itemManager;
    }
}
