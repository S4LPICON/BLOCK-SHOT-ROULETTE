package com.s4lpicon.blockShotRoulette.listener.game.player;

import com.s4lpicon.blockShotRoulette.event.player.BlockShotPlayerDamageEvent;
import com.s4lpicon.blockShotRoulette.model.BlockShotPlayer;
import net.kyori.adventure.text.Component;
import org.bukkit.Bukkit;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;

public class BlockShotPlayerDamageListener implements Listener {

    @EventHandler
    public void onDamage(BlockShotPlayerDamageEvent event) {
        BlockShotPlayer victim = event.getVictim();
        Bukkit.broadcast(Component.text("-------------------------------------------"));
        event.getShooter().ifPresent(shooter -> {
            if (victim.equals(shooter)) {
                // Tu lógica aquí
                Bukkit.broadcast(Component.text(event.getShooter().get().getPlayer().getName() +" SE HA DISPARADO EL MISMO!"));
            }
        });
        Bukkit.broadcast(Component.text(event.getVictim().getPlayer().getName() + " Ha recibido " + event.getDamage() + " de daño!"));
        Bukkit.broadcast(Component.text("LA NUEVA VIDA DE " + event.getVictim().getPlayer().getName() + " es " + event.getVictim().getEnergy()));
        Bukkit.broadcast(Component.text("-------------------------------------------"));
    }

}
