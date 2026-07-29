package com.s4lpicon.blockShotRoulette.command;

import com.s4lpicon.blockShotRoulette.BlockShotRoulette;
import com.s4lpicon.blockShotRoulette.event.BlockShotPlayerShootEvent;
import com.s4lpicon.blockShotRoulette.event.BlockShotPlayerUseItemEvent;
import com.s4lpicon.blockShotRoulette.model.BlockShotPlayer;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

public class UseItemTestCommand implements CommandExecutor {
    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String @NotNull [] args) {

        if (!command.getName().equalsIgnoreCase("use-item")){
            return true;
        }
        if (!(sender instanceof Player player)){
            return true;
        }

        if (args.length < 1){
            player.sendMessage("que slot de item vas a usar");
            return true;
        }
        BlockShotPlayer whoUseItem = BlockShotRoulette.getInstance().getBlockShotGameManager().getBlockShotPlayer(player);
        if (whoUseItem  == null) {
            player.sendMessage("No estás en una partida.");
            return true;
        }
        try {
            int slot = Integer.parseInt(args[0]);
            whoUseItem.getItemManager().useItem(slot);
        } catch (NumberFormatException e) {
            sender.sendMessage("El slot debe ser un número.");
        }

        return true;
    }
}
