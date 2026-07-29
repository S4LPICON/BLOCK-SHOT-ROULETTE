package com.s4lpicon.blockShotRoulette.task;

import com.s4lpicon.blockShotRoulette.util.TargetUtil;
import net.kyori.adventure.text.Component;
import org.bukkit.entity.Entity;
import org.bukkit.entity.ItemDisplay;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;

public class AimItemTask extends BukkitRunnable {

    private final Player player;
    private Entity currentItem;

    public AimItemTask(Player player) {
        this.player = player;
    }

    @Override
    public void run() {
//        Entity target = TargetUtil.getTarget(
//                player,
//                20,
//                entity -> entity instanceof ItemDisplay
//        );
        Entity target = TargetUtil.getTarget(
                player,
                20,
                entity -> entity instanceof ItemDisplay
        );

        if (target == currentItem) {
            return;
        }

        if (currentItem != null) {
            currentItem.setGlowing(false);
        }

        if (target != null) {
            target.setGlowing(true);

            if (target instanceof ItemDisplay display) {
                player.sendActionBar(Component.text("Vas a usar el item: " ));
            }

        currentItem = target;
    }}

    public void stop() {

        if (currentItem != null) {
            currentItem.setGlowing(false);
        }

        player.sendActionBar(Component.empty());

        cancel();
    }

    public Entity getCurrentItem() {
        return currentItem;
    }
}