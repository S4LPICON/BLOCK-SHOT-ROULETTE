package com.s4lpicon.blockShotRoulette.command;

import com.s4lpicon.blockShotRoulette.BlockShotRoulette;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;
import org.jspecify.annotations.NonNull;

import java.util.ArrayList;
import java.util.List;

public class TestCommand implements CommandExecutor {

    private final BlockShotRoulette plugin;

    public TestCommand(BlockShotRoulette plugin) {
        this.plugin = plugin;
    }

    @Override
    public boolean onCommand(
            @NotNull CommandSender sender,
            @NotNull Command command,
            @NotNull String label,
            @NotNull String @NonNull [] args
    ) {

        if (!(sender instanceof Player player)) {
            sender.sendMessage("Only players");
            return true;
        }

        List<Player> players = new ArrayList<>(Bukkit.getOnlinePlayers());

        if (players.size() > 4) {
            sender.sendMessage("Máximo 4 jugadores");
            return true;
        }

        plugin.getBlockShotGameManager().createGame(players);

        player.sendMessage("Se creó la partida");

        return true;
    }
}