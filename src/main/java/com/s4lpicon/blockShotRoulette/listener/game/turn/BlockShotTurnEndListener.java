package com.s4lpicon.blockShotRoulette.listener.game.turn;

import com.s4lpicon.blockShotRoulette.event.turn.BlockShotTurnEndEvent;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;

public class BlockShotTurnEndListener implements Listener {

    @EventHandler
    public void onTurnEnd(BlockShotTurnEndEvent event){
        event.getPlayer().getPlayer().sendMessage("Se acabo tu turno!");
    }
}
