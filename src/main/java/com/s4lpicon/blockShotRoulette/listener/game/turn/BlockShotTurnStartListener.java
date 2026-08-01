package com.s4lpicon.blockShotRoulette.listener.game.turn;

import com.s4lpicon.blockShotRoulette.event.turn.BlockShotTurnStartEvent;
import net.kyori.adventure.text.Component;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;


public class BlockShotTurnStartListener implements Listener {
    @EventHandler
    public void onTurnStart(BlockShotTurnStartEvent event){
        Bukkit.broadcast(Component.text("-------------------------------------"));
        Bukkit.broadcast(Component.text("Le toca disparar a " + event.getPlayer().getPlayer().getName()));
        Bukkit.broadcast(Component.text("escopeta: " + event.getPlayer().getBlockShotGame().getShotGun().getShells()));
        Bukkit.broadcast(Component.text("-------------------------------------"));
        event.getPlayer().getPlayer().sendMessage("¡Te toca a ti elige a quien disparar!");
    }
}
