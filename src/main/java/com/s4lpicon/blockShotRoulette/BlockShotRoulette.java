package com.s4lpicon.blockShotRoulette;

import com.s4lpicon.blockShotRoulette.command.TestCommand;
import com.s4lpicon.blockShotRoulette.command.UseItemTestCommand;
import com.s4lpicon.blockShotRoulette.listener.*;
import com.s4lpicon.blockShotRoulette.manager.BlockShotGameManager;
import com.s4lpicon.blockShotRoulette.manager.ItemEffectManager;
import org.bukkit.Bukkit;
import org.bukkit.World;
import org.bukkit.entity.Entity;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.Objects;

public final class BlockShotRoulette extends JavaPlugin {

    private final BlockShotGameManager blockShotGameManager = new BlockShotGameManager(this);

    private static BlockShotRoulette instance;

    private final ItemEffectManager itemEffectManager = new ItemEffectManager();

    @Override
    public void onEnable() {
        instance = this;
        // Plugin startup logic
        getServer().getPluginManager().registerEvents(new PlayerInteractListener(this), this);

        getServer().getPluginManager().registerEvents(new BlockShotPlayerShootListener(),this);

        getServer().getPluginManager().registerEvents(new VehicleExitListener(),this);

        getServer().getPluginManager().registerEvents(new BlockShotPlayerUseItemListener(itemEffectManager),this);

        getServer().getPluginManager().registerEvents(new BlockShotPlayerDeathListener(),this);

        getServer().getPluginManager().registerEvents(new BlockShotPlayerDamageListener(),this);

        Objects.requireNonNull(getCommand("test")).setExecutor(new TestCommand(this));

        Objects.requireNonNull(getCommand("use-item")).setExecutor(new UseItemTestCommand());
        getLogger().info("Plugin has been enabled!");
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

    public static BlockShotRoulette getInstance(){
        return instance;
    }
}
