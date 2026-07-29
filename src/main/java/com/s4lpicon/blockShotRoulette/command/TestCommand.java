package com.s4lpicon.blockShotRoulette.command;

import com.s4lpicon.blockShotRoulette.BlockShotRoulette;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

import java.util.List;

public class TestCommand implements CommandExecutor {

    private BlockShotRoulette plugin;

    public TestCommand(BlockShotRoulette plugin){

        this.plugin = plugin;
    }

    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String @NotNull [] args) {

        if (!command.getName().equalsIgnoreCase("test")){
            return true;
        }

        if (!(sender instanceof Player player)){

            sender.sendMessage("Only players");

            return true;

        }

        plugin.getBlockShotGameManager().createGame((List<Player>) Bukkit.getServer().getOnlinePlayers());
        player.sendMessage("SE creo el juego todos han sido tpeados");

        return true;
    }
}
