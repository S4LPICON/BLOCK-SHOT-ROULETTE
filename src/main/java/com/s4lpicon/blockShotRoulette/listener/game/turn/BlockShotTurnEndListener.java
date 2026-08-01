package com.s4lpicon.blockShotRoulette.listener.game.turn;

import com.s4lpicon.blockShotRoulette.event.turn.BlockShotTurnEndEvent;
import net.kyori.adventure.text.Component;
import org.bukkit.Bukkit;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;

public class BlockShotTurnEndListener implements Listener {

    @EventHandler
    public void onTurnEnd(BlockShotTurnEndEvent event){

        Bukkit.broadcast(Component.text("-------------------------------------"));
        Bukkit.broadcast(Component.text("Se acabo el turno de" + event.getPlayer().getPlayer().getName()));
        Bukkit.broadcast(Component.text("-------------------------------------"));
    }
}
