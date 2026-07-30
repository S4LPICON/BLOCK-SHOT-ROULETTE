package com.s4lpicon.blockShotRoulette.listener;

import com.s4lpicon.blockShotRoulette.BlockShotRoulette;
import com.s4lpicon.blockShotRoulette.event.player.BlockShotPlayerShootEvent;
import com.s4lpicon.blockShotRoulette.model.BlockShotPlayer;
import com.s4lpicon.blockShotRoulette.task.AimTask;
import org.bukkit.Bukkit;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Interaction;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.EquipmentSlot;

public class PlayerInteractListener implements Listener {

    private final BlockShotRoulette plugin;

    public PlayerInteractListener(BlockShotRoulette plugin){
        this.plugin = plugin;
    }

    @EventHandler
    public void onInteract(PlayerInteractEvent event) {

        if (event.getHand() != EquipmentSlot.HAND) {
            return;
        }
        BlockShotPlayer shooter = plugin.getBlockShotGameManager().getBlockShotPlayer(event.getPlayer());

        if (shooter==null) return;

        AimTask task = shooter.getAimTask();

        if (task == null) return;

        Entity entity = task.getCurrentTarget();
        handleShotEvent(shooter, entity);
    }

    @EventHandler
    public void onDamage(EntityDamageByEntityEvent event) {

        if (!(event.getDamager() instanceof Player player)) {
            return;
        }

        if (!(event.getEntity() instanceof Interaction interaction)) {
            return;
        }

        BlockShotPlayer shooter = plugin.getBlockShotGameManager().getBlockShotPlayer(player);


        Entity entity = event.getEntity();

        handleShotEvent(shooter, entity);
    }

    private void handleShotEvent(BlockShotPlayer shooter, Entity entity){

        if (shooter.getAimTask() == null)return;


        shooter.getPlayer().sendMessage("Listener triggered");

        if (entity==null){
            return;
        }

        BlockShotPlayer target;
        if (entity instanceof Interaction) {
            target = shooter;
        } else if (entity instanceof Player targetPlayer) {
            target = plugin.getBlockShotGameManager().getBlockShotPlayer(targetPlayer);
        } else {
            return;
        }


        if (target == null){
            return;
        }


        Bukkit.getPluginManager().callEvent(
                new BlockShotPlayerShootEvent(shooter, target)
        );
    }

}
