package com.s4lpicon.blockShotRoulette.task;

import com.s4lpicon.blockShotRoulette.util.TargetUtil;
import net.kyori.adventure.text.Component;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;

public class AimPlayerTask extends BukkitRunnable {

    private final Player player;
    private Entity currentTarget;

    public AimPlayerTask(Player player) {
        this.player = player;
    }

    @Override
    public void run() {
        Entity target = TargetUtil.getTarget(
                player,
                20,
                entity -> entity instanceof Player && entity != player
        );

        if (target == currentTarget) {
            return;
        }

        if (currentTarget != null) {
            currentTarget.setGlowing(false);
        }

        if (target != null) {
            target.setGlowing(true);

            if (target instanceof Player p) {
                player.sendActionBar(Component.text("Vas a disparar a: " + p.getName()));
            } else {
                player.sendActionBar(Component.text("Vas a dispararte"));
            }}
//        } else {
//            player.sendActionBar(Component.empty());
//        }

        currentTarget = target;
    }

    public void stop() {

        if (currentTarget != null) {
            currentTarget.setGlowing(false);
        }

        player.sendActionBar(Component.empty());

        cancel();
    }

    public Entity getCurrentTarget() {
        return currentTarget;
    }
}