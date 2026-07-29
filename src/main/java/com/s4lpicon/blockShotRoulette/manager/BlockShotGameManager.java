package com.s4lpicon.blockShotRoulette.manager;

import com.s4lpicon.blockShotRoulette.BlockShotRoulette;
import com.s4lpicon.blockShotRoulette.registry.MapRegistry;
import com.s4lpicon.blockShotRoulette.model.BlockShotGame;
import com.s4lpicon.blockShotRoulette.model.BlockShotPlayer;
import com.s4lpicon.blockShotRoulette.registry.settings.MapSettings;
import com.s4lpicon.blockShotRoulette.util.SeatUtil;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

import java.util.*;

public class BlockShotGameManager {
    private final Set<BlockShotGame> activeGames= new HashSet<>();
    private final Map<UUID, BlockShotPlayer> activePlayers = new HashMap<>();
    private final BlockShotRoulette plugin;


    public BlockShotGameManager(BlockShotRoulette plugin){
        this.plugin = plugin;
    }


    public void createGame(@NotNull List<Player> players){
        BlockShotGame game = new BlockShotGame(players, plugin);
        for (BlockShotPlayer player : game.getPlayers()) {
            activePlayers.put(
                    player.getPlayer().getUniqueId(),
                    player
            );
        }
        teleportPlayersToSeats(players);
        game.startGame();
    }

    private void teleportPlayersToSeats(@NotNull List<Player> players){
        int count =0;
        for (Player player : players) {
            teleportPlayerToSeat(player, count);

            count++;
        }
    }

    private void teleportPlayerToSeat(Player player, int count){
        MapSettings mapSettings = MapRegistry.MAPS.getFirst();//las configs del mapa
        player.teleportAsync(mapSettings.getSitLocations().get(count));
        SeatUtil.sit(plugin, player, mapSettings.getSitLocations().get(count));
    }

    public BlockShotPlayer getBlockShotPlayer(@NotNull Player player){
        return activePlayers.get(player.getUniqueId());
    }
}
