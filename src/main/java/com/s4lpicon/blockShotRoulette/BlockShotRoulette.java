package com.s4lpicon.blockShotRoulette;

import com.s4lpicon.blockShotRoulette.command.TestCommand;
import com.s4lpicon.blockShotRoulette.command.UseItemTestCommand;
import com.s4lpicon.blockShotRoulette.event.EventDispatcher;
import com.s4lpicon.blockShotRoulette.listener.game.item.BlockShotItemGiveFailedListener;
import com.s4lpicon.blockShotRoulette.listener.game.item.BlockShotItemGivenListener;
import com.s4lpicon.blockShotRoulette.listener.game.item.BlockShotItemRemovedListener;
import com.s4lpicon.blockShotRoulette.listener.game.item.BlockShotItemUsedListener;
import com.s4lpicon.blockShotRoulette.listener.game.match.BlockShotMatchEndListener;
import com.s4lpicon.blockShotRoulette.listener.game.player.*;
import com.s4lpicon.blockShotRoulette.listener.game.shotgun.BlockShotShotgunShellEjectedListener;
import com.s4lpicon.blockShotRoulette.listener.game.turn.BlockShotTurnEndListener;
import com.s4lpicon.blockShotRoulette.listener.game.turn.BlockShotTurnStartListener;
import com.s4lpicon.blockShotRoulette.listener.vanilla.PlayerInteractListener;
import com.s4lpicon.blockShotRoulette.listener.vanilla.VehicleExitListener;
import com.s4lpicon.blockShotRoulette.manager.BlockShotGameManager;
import com.s4lpicon.blockShotRoulette.manager.ItemEffectManager;
import com.s4lpicon.blockShotRoulette.manager.ItemManager;
import org.bukkit.Bukkit;
import org.bukkit.World;
import org.bukkit.entity.Entity;
import org.bukkit.event.Listener;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.Objects;

public final class BlockShotRoulette extends JavaPlugin {

    private final BlockShotGameManager blockShotGameManager = new BlockShotGameManager(this);

    private static BlockShotRoulette instance;

    private final ItemEffectManager itemEffectManager = new ItemEffectManager();

    private final ItemManager itemManager = new ItemManager(itemEffectManager);

    private final EventDispatcher eventDispatcher = new EventDispatcher();

    @Override
    public void onEnable() {
        instance = this;
        // Plugin startup logic
        registerVanillaListeners();
        registerPlayerListeners();
        registerItemsListeners();
        registerTurnsListeners();
        registerShotgunListeners();

        Objects.requireNonNull(getCommand("test")).setExecutor(new TestCommand(this));

        Objects.requireNonNull(getCommand("use-item")).setExecutor(new UseItemTestCommand(this));
        getLogger().info("Plugin has been enabled!");
    }

    private void registerVanillaListeners(){
        registerListeners(
                new PlayerInteractListener(this),
                new VehicleExitListener()

        );
    }

    private void registerPlayerListeners() {
        registerListeners(
                new BlockShotPlayerHealListener(),
                new BlockShotPlayerDamageListener(),
                new BlockShotPlayerDeathListener(),
                new BlockShotPlayerShootListener()

        );
    }

    private void registerItemsListeners() {
        registerListeners(
                new BlockShotItemGiveFailedListener(),
                new BlockShotItemGivenListener(),
                new BlockShotItemUsedListener(),
                new BlockShotItemRemovedListener()

        );
    }

    private void registerTurnsListeners() {
        registerListeners(
                new BlockShotTurnStartListener(),
                new BlockShotTurnEndListener()

        );
    }

    private void registerShotgunListeners() {
        registerListeners(
                new BlockShotShotgunShellEjectedListener()

        );
    }

    private void registerMatchListeners() {
        registerListeners(
                new BlockShotMatchEndListener()

        );
    }

    private void registerListeners(Listener... listeners) {
        PluginManager pm = getServer().getPluginManager();

        for (Listener listener : listeners) {
            pm.registerEvents(listener, this);
        }
    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
        for (World world : Bukkit.getWorlds()) {
            for (Entity entity : world.getEntities()) {
                if (entity.isGlowing()) {
                    entity.setGlowing(false);
                }
            }
        }
        getLogger().info("Plugin has been disable!");
    }

    public BlockShotGameManager getBlockShotGameManager(){
        return this.blockShotGameManager;
    }

    public ItemManager getItemManager(){
        return this.itemManager;
    }

    public static BlockShotRoulette getInstance(){
        return instance;
    }
}
