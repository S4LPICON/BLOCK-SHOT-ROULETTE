package com.s4lpicon.blockShotRoulette.listener.game.player;

import com.s4lpicon.blockShotRoulette.event.item.BlockShotItemUsedEvent;
import com.s4lpicon.blockShotRoulette.event.player.BlockShotPlayerShootDeniedEvent;
import com.s4lpicon.blockShotRoulette.model.BlockShotPlayer;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;

import java.util.Arrays;

public class BlockShotPlayerShootDeniedListener implements Listener {

    @EventHandler
    public void onShootDenied(BlockShotPlayerShootDeniedEvent event) {
        switch (event.getReason()){
            case DEAD -> handleDead(event.getShooter());
            case NOT_YOUR_TURN -> handleNotTurn(event.getShooter());
        }
    }


    public void handleDead(BlockShotPlayer player){
        player.getPlayer().sendMessage("Estas muerto!");
    }

    public void handleNotTurn(BlockShotPlayer player){
        player.getPlayer().sendMessage("No es tu turno");
    }
}
