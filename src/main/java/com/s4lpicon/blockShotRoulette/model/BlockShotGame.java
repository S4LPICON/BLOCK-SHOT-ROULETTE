package com.s4lpicon.blockShotRoulette.model;

import com.s4lpicon.blockShotRoulette.item.BlockShotItemType;
import com.s4lpicon.blockShotRoulette.item.ShotGun;
import com.s4lpicon.blockShotRoulette.manager.ItemManager;
import com.s4lpicon.blockShotRoulette.registry.RoundRegistry;
import com.s4lpicon.blockShotRoulette.registry.settings.RoundSettings;
import com.s4lpicon.blockShotRoulette.manager.TurnManager;
import com.s4lpicon.blockShotRoulette.state.GameState;
import com.s4lpicon.blockShotRoulette.state.PlayerState;
import com.s4lpicon.blockShotRoulette.task.AimTask;
import com.s4lpicon.blockShotRoulette.util.ItemPoolUtil;
import com.s4lpicon.blockShotRoulette.util.TaskUtil;
import net.kyori.adventure.text.Component;
import org.bukkit.Bukkit;
import org.bukkit.GameMode;
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
    private int sequence = 1;
    private int round = 0;


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

        startRound();

    }

    public void startRound() {
        RoundSettings roundSettings = RoundRegistry.ROUNDS.get(round);

        for (BlockShotPlayer player : players) {
            player.setEnergy(roundSettings.getLives());
            // RESTABLECER ESTADO DE LOS JUGADORES:
            player.setPlayerState(PlayerState.WAITING);
            if (player.getPlayer().getGameMode() == GameMode.SPECTATOR ){
                player.getPlayer().setGameMode(GameMode.ADVENTURE);
            }
        }

        startSequence();
    }

    public void startSequence(){
        RoundSettings roundSettings = RoundRegistry.ROUNDS.get(round);//duplicado

        this.shotGun = new ShotGun(roundSettings.getLiveShells(), roundSettings.getBlankShells()); //recarga la escopeta

        //da los items
        for (BlockShotPlayer player : players) {
            ItemManager itemManager = player.getItemManager();
            for (int i = 0; i < roundSettings.getItemsPerPlayer(); i++) {

                BlockShotItemType item = ItemPoolUtil.randomItem();

                if (itemManager.giveItem(item)){
                    player.getPlayer().sendMessage("Has recibo un: "+ item);
                }else {
                    player.getPlayer().sendMessage("NO pudiste recibir el item");
                }
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


    public void nextTurn() {
        int playersAlive = 0;
        for (BlockShotPlayer player : players) {
            if (!player.getPlayerState().equals(PlayerState.DEAD)) {
                playersAlive++;
            }
        }

        if (playersAlive < 2) {
            Bukkit.getServer().broadcast(Component.text("YA MURIERON EMPEZANDO NUEVA RONDA"));
            round++;
            Bukkit.getServer().broadcast(Component.text("EMPEZANDO LA RONDA: "+ round+1));
            TaskUtil.waitTicks(40, () ->{
                // Ejecutar en el siguiente tick para vaciar el call stack actual
                Bukkit.getScheduler().runTask(plugin, this::startRound);
            });
            return;
        }

        Bukkit.getServer().broadcast(Component.text("COMO HAY MAS DE 1 CON VIDA SIGUE LA MISMA RONDA"));

        if (shotGun.isEmpty()) {
            Bukkit.getServer().broadcast(Component.text("SE QUEDO SIN BALAS LA ESCOPETA, CAMBIANDO A OTRA SECUENCIA"));

            // Ejecutar en el siguiente tick
            Bukkit.getScheduler().runTask(plugin, this::startSequence);
            return;
        }

        BlockShotPlayer turnPlayer = turnManager.getActualTurnPlayer();
        for (BlockShotPlayer player : players) {
            player.getPlayer().sendMessage("Ahora le toca a:" + turnPlayer.getPlayer().getName());
            player.getPlayer().sendMessage("Orden de las balas: " + shotGun.toString());
            player.getPlayer().sendMessage("tus items son: " + Arrays.toString(player.getItems()));
            player.getPlayer().sendMessage("te quedan " + player.getEnergy() + " vidas");
        }
        startTurn(turnPlayer);
    }

    public void startTurn(@NotNull BlockShotPlayer player){
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
        if (!(shooterPlayer.getAimTask() == null)){
            shooterPlayer.getAimTask().stop();
        }

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

                targetPlayer.takeDamage(shooterPlayer, damage);
                shooterPlayer.setPlayerState(PlayerState.WAITING);
                turnManager.next();
                nextTurn();
            }
        }
        if (shotGun.isSawedOff()){
            shotGun.setSawedOff(false);
            //podriamos llamar evento de que se le quita lo recortada
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