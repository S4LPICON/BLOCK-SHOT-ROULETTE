package com.s4lpicon.blockShotRoulette.model;

import com.s4lpicon.blockShotRoulette.item.BlockShotItemType;
import com.s4lpicon.blockShotRoulette.item.ShotGun;
import com.s4lpicon.blockShotRoulette.manager.ItemManager;
import com.s4lpicon.blockShotRoulette.registry.RoundRegistry;
import com.s4lpicon.blockShotRoulette.registry.settings.RoundSettings;
import com.s4lpicon.blockShotRoulette.manager.TurnManager;
import com.s4lpicon.blockShotRoulette.state.GameState;
import com.s4lpicon.blockShotRoulette.state.PlayerState;
import com.s4lpicon.blockShotRoulette.task.AimItemTask;
import com.s4lpicon.blockShotRoulette.task.AimTask;
import com.s4lpicon.blockShotRoulette.util.ItemPoolUtil;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class BlockShotGame {

    private final List<@NotNull BlockShotPlayer> players = new ArrayList<>(4);
    private final TurnManager turnManager;
    private ShotGun shotGun;
    private GameState gameState = GameState.WAITING;
    private final JavaPlugin plugin;


    AimTask aimTask;
    //AimItemTask aimItemTask;



    public BlockShotGame(@NotNull  List<Player> players, JavaPlugin plugin) {
        if (players.size() > 4) {
            throw new IllegalArgumentException("A BlockShotGame can have a maximum of 4 players.");
        }

        for (Player player : players){
            this.players.add(new BlockShotPlayer(player, this));
        }
        this.plugin = plugin;
        this.turnManager = new TurnManager(this.players);
    }

    public void startGame(){
        this.gameState = GameState.STARTING;
        RoundSettings roundSettings = RoundRegistry.ROUNDS.getFirst();
        this.shotGun = new ShotGun(roundSettings.getLiveShells(), roundSettings.getBlankShells());
        for (BlockShotPlayer player : players){
            player.setEnergy(roundSettings.getLives());
        }
        startRound();

    }

    public void startRound(){
        RoundSettings settings = RoundRegistry.ROUNDS.getFirst();

        for (BlockShotPlayer player : players) {
            ItemManager itemManager = player.getItemManager();
            for (int i = 0; i < settings.getItemsPerPlayer(); i++) {

                BlockShotItemType item = ItemPoolUtil.randomItem();

                if (itemManager.giveItem(item)){
                    player.getPlayer().sendMessage("Has recibo un: "+ item);
                }

                player.getPlayer().sendMessage("NO pudiste recibir el item");

            }
        }
        nextTurn();



    }
    public void endRound(){
        for (BlockShotPlayer player : players){
            player.getPlayer().sendMessage("FIN DE LA RONDA");
        }
        //nueva ronda
    }


    public void nextTurn(){

        if (shotGun.isEmpty()){
            endRound();
            return;
        }
        BlockShotPlayer turnPlayer = turnManager.getActualTurnPlayer();
        for (BlockShotPlayer player : players){
            player.getPlayer().sendMessage("Ahora le toca a:" + turnPlayer.getPlayer().getName());
            player.getPlayer().sendMessage("Orden de las balas: "+ shotGun.toString());
            player.getPlayer().sendMessage("tus items son: " + Arrays.toString(player.getItems()));
            player.getPlayer().sendMessage("te quedan "+ player.getEnergy() + "vidas");
        }
        startTurn(turnPlayer);
    }

    public void startTurn(@NotNull BlockShotPlayer player){
//        this.aimItemTask = new AimItemTask(player.getPlayer());
//        aimItemTask.runTaskTimer(plugin, 0L, 1L);
//        player.setAimItemTask(aimItemTask);

        this.aimTask = new AimTask(player.getPlayer());
        aimTask.runTaskTimer(plugin, 0L, 1L);
        player.setAimTask(aimTask);

        player.getPlayer().sendMessage("Te toca a ti elije a quien disparar");

        player.setPlayerState(PlayerState.PLAYING);

    }
    public void handleShoot(BlockShotPlayer shooter, BlockShotPlayer target){

        if (shooter.getPlayerState() == PlayerState.DEAD) {
            shooter.getPlayer().sendMessage("Estás muerto.");
            return;
        }

        if (shooter.getPlayerState() == PlayerState.WAITING) {
            shooter.getPlayer().sendMessage("No es tu turno.");
            return;
        }

        if (shooter.getPlayer().getName().equalsIgnoreCase(target.getPlayer().getName())){
            shooter.getPlayer().sendMessage("Te disparaste tu mismo!");
        }else {
            shooter.getPlayer().sendMessage("disparaste a: "+ target.getPlayer().getName());
        }

        shoot(shooter, target);
    }

    public void shoot(BlockShotPlayer shooterPlayer, BlockShotPlayer targetPlayer) {
        if (shotGun.isEmpty()){
            return;
        }
        shooterPlayer.getAimTask().stop();
        switch (shotGun.shoot()) {

            case BLANK -> {
                boolean shotSelf = shooterPlayer.equals(targetPlayer);
                if (shotSelf) {
                    nextTurn();
                } else {
                    turnManager.next();
                    nextTurn();
                }
            }

            case LIVE -> {

                int damage = shotGun.isSawedOff() ? 2 : 1;

                targetPlayer.damage(damage);
                shooterPlayer.setPlayerState(PlayerState.WAITING);
                turnManager.next();
                nextTurn();
            }
        }
    }


    public GameState getGameState(){
        return this.gameState;
    }

    public List<BlockShotPlayer> getPlayers(){
        return this.players;
    }


    public ShotGun getShotGun(){
        return this.shotGun;
    }
}