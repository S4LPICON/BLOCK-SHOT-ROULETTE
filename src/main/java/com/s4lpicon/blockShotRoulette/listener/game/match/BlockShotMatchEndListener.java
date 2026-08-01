package com.s4lpicon.blockShotRoulette.listener.game.match;

import com.s4lpicon.blockShotRoulette.event.match.BlockShotMatchEndEvent;
import com.s4lpicon.blockShotRoulette.model.BlockShotPlayer;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.NamedTextColor;
import org.bukkit.Bukkit;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;

public class BlockShotMatchEndListener implements Listener {

    @EventHandler
    public void onMatchEnd(BlockShotMatchEndEvent event) {
        Bukkit.getLogger().info("jugadores cuando se termino el juego: " + event.getGame().getPlayers());
        for (BlockShotPlayer player : event.getGame().getPlayers()) {

            player.getPlayer().sendMessage(
                    Component.text("HA TERMINADO EL JUEGO", NamedTextColor.BLUE)
            );

            player.getPlayer().sendMessage(
                    Component.text("El ganador fue: ", NamedTextColor.YELLOW)
                            .append(Component.text(
                                    event.getWinner().getPlayer().getName(),
                                    NamedTextColor.GOLD
                            ))
            );
        }
    }
}