package com.s4lpicon.blockShotRoulette.listener.game.shotgun;

import com.s4lpicon.blockShotRoulette.event.shotgun.BlockShotShotgunReloadEvent;
import com.s4lpicon.blockShotRoulette.model.BlockShotPlayer;
import net.kyori.adventure.text.Component;
import org.bukkit.Bukkit;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;

public class BlockShotShotgunReloadListener implements Listener {

    @EventHandler
    public void onShotgunReload(BlockShotShotgunReloadEvent event) {

        Bukkit.broadcast(Component.text("-------------------------------------"));
        Bukkit.broadcast(Component.text("La escopeta ha sido recargada"));
        Bukkit.broadcast(Component.text("las balas de la escopetas som: " + event.getShotGun().getShells()));
        Bukkit.broadcast(Component.text("-------------------------------------"));
    }
}