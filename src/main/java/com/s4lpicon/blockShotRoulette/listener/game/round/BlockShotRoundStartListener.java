package com.s4lpicon.blockShotRoulette.listener.game.round;

import com.s4lpicon.blockShotRoulette.event.round.BlockShotRoundStartEvent;
import com.s4lpicon.blockShotRoulette.model.BlockShotPlayer;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.NamedTextColor;
import org.bukkit.Bukkit;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;

public class BlockShotRoundStartListener implements Listener {

    @EventHandler
    public void onRoundStart(BlockShotRoundStartEvent event){
        Bukkit.broadcast(Component.text("HA EMPEZADO LA RONDA: ", NamedTextColor.YELLOW)
                .append(Component.text(
                        event.getGame().getRound(),
                        NamedTextColor.GOLD
                )));
    }
}