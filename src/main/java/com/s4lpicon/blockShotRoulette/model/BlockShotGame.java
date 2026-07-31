package com.s4lpicon.blockShotRoulette.model;

import com.s4lpicon.blockShotRoulette.BlockShotRoulette;
import com.s4lpicon.blockShotRoulette.event.player.BlockShotPlayerShootDeniedEvent;
import com.s4lpicon.blockShotRoulette.event.player.BlockShotPlayerShootEvent;
import com.s4lpicon.blockShotRoulette.event.player.model.ShootDeniedReason;
import com.s4lpicon.blockShotRoulette.event.turn.BlockShotTurnEndEvent;
import com.s4lpicon.blockShotRoulette.event.turn.BlockShotTurnStartEvent;
import com.s4lpicon.blockShotRoulette.item.type.BlockShotItemType;
import com.s4lpicon.blockShotRoulette.item.ShotGun;
import com.s4lpicon.blockShotRoulette.item.type.ShellType;
import com.s4lpicon.blockShotRoulette.manager.ItemManager;
import com.s4lpicon.blockShotRoulette.registry.RoundRegistry;
import com.s4lpicon.blockShotRoulette.registry.settings.RoundSettings;
import com.s4lpicon.blockShotRoulette.manager.TurnManager;
import com.s4lpicon.blockShotRoulette.state.GameState;
import com.s4lpicon.blockShotRoulette.state.PlayerState;
import com.s4lpicon.blockShotRoulette.state.ShotGunState;
import com.s4lpicon.blockShotRoulette.task.AimPlayerTask;
import com.s4lpicon.blockShotRoulette.util.ItemPoolUtil;
import com.s4lpicon.blockShotRoulette.util.TaskUtil;
import net.kyori.adventure.text.Component;
import org.bukkit.Bukkit;
import org.bukkit.GameMode;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class BlockShotGame {

    private final List<@NotNull BlockShotPlayer> players = new ArrayList<>(4);
    private final TurnManager turnManager;
    private final ShotGun shotGun;
    private GameState gameState = GameState.WAITING;
    private final ItemManager itemManager;
    private final BlockShotRoulette plugin;
    //private int sequence = 1;
    private int round = 0;


    public BlockShotGame(@NotNull  List<Player> players, BlockShotRoulette plugin) {
        if (players.size() > 4) {
            throw new IllegalArgumentException("A BlockShotGame can have a maximum of 4 players.");
        }
        int MAX_SHELLS = 10;
        this.shotGun = new ShotGun(MAX_SHELLS);
        for (Player player : players){
            this.players.add(new BlockShotPlayer(player, this));
        }
        this.plugin = plugin;
        this.turnManager = new TurnManager(this.players);
        this.itemManager = plugin.getItemManager();
    }

    public void startGame(){
        this.gameState = GameState.STARTING;
        //TODO call BlockShotMatchStartEvent
        startRound();
    }

    public void endGame(){
        //TODO logica para terminar el juego
        //TODO call BlockShotMatchEndEvent
    }

    public void startRound() {
        RoundSettings roundSettings = RoundRegistry.ROUNDS.get(round); //TODO aqui se daña cuando se llega a la ronda 4 (implementar logica de terminar juego)
        //TODO: llamar a el evento BlockShotRoundStartEvent
        resetPlayerStates(roundSettings);
        startSequence(roundSettings);
    }

    public void resetPlayerStates(RoundSettings roundSettings){
        for (BlockShotPlayer player : players) {
            player.setEnergy(roundSettings.getLives());
            // RESTABLECER ESTADO DE LOS JUGADORES:
            player.setPlayerState(PlayerState.WAITING);
            if (player.getPlayer().getGameMode() == GameMode.SPECTATOR ){
                player.getPlayer().setGameMode(GameMode.ADVENTURE);
            }
        }
    }

    public void giveItems(RoundSettings roundSettings){
        //da los items
        for (BlockShotPlayer player : players) {
            for (int i = 0; i < roundSettings.getItemsPerPlayer(); i++) {

                BlockShotItemType item = ItemPoolUtil.randomItem();

                itemManager.giveItem(player, item);
            }
        }
    }

    public void startSequence(RoundSettings roundSettings){

        reloadShotGun(roundSettings);
        giveItems(roundSettings);
        processNextTurn();
    }

    public void reloadShotGun(RoundSettings roundSettings){
        this.shotGun.reload(roundSettings.getLiveShells(), roundSettings.getBlankShells()); //recarga la escopeta
    }


    public void endRound(){
        //TODO: llama al evento BlockShotRoundEndEvent
        //TODO eliminar todos los items de todos los jugadores
        //TODO entregarle el premio al ganador
    }

    public void processNextTurn(){

        if(hasRoundEnded()){
            endRound();
            startNextRound();
            return;
        }

        if(shotGun.isEmpty()){
            startNextSequence();
            return;
        }

        BlockShotPlayer player = turnManager.getActualTurnPlayer();

        sendTurnInfo(player);
        startTurn(player);
    }

    private boolean hasRoundEnded(){

        long alivePlayers = players.stream()
                .filter(player -> player.getPlayerState() != PlayerState.DEAD)
                .count();

        return alivePlayers < 2;
    }

    private void startNextRound(){

        round++;

        Bukkit.broadcast(
                Component.text("EMPEZANDO LA RONDA: " + round)
        );

        TaskUtil.waitTicks(40, () -> Bukkit.getScheduler().runTask(
                plugin,
                this::startRound
        ));
    }

    private void startNextSequence(){
        //TODO call BlockShotRoundReloadEvent

        Bukkit.getScheduler().runTask(
                plugin,
                () -> startSequence(
                        RoundRegistry.ROUNDS.get(round)
                )
        );
    }
    @Deprecated(forRemoval = true)
    private void sendTurnInfo(BlockShotPlayer turnPlayer){

        for(BlockShotPlayer player : players){

            player.getPlayer().sendMessage(
                    "Ahora le toca a: " + turnPlayer.getPlayer().getName()
            );

            player.getPlayer().sendMessage(
                    "Balas: " + shotGun
            );

            player.getPlayer().sendMessage(
                    "Items: " + Arrays.toString(player.getItems())
            );

            player.getPlayer().sendMessage(
                    "Vidas: " + player.getEnergy()
            );
        }
    }

    public void startTurn(@NotNull BlockShotPlayer player){
        //task para que pueda apuntar a un jugador
        /*TODO
        *  separar en un metodo el dar el aimtask
        *  implementar el AimItemTask al inicio de la ronda en lugar de AimPlayerTask
        *  cuando seleccionen la escopeta ahi si se le da el aimtask
        *
        * */
        AimPlayerTask aimPlayerTask = new AimPlayerTask(player.getPlayer());

        aimPlayerTask.runTaskTimer(plugin,0L,1L);

        player.setAimTask(aimPlayerTask);
        //-----------------------------------------

        Bukkit.getPluginManager().callEvent(
                new BlockShotTurnStartEvent(
                        this,
                        player
                )
        );

        player.setPlayerState(PlayerState.PLAYING);

    }
    public void handleShoot(BlockShotPlayer shooter, BlockShotPlayer target){

        if (shooter.getPlayerState() == PlayerState.DEAD) {
            Bukkit.getPluginManager().callEvent(
                    new BlockShotPlayerShootDeniedEvent(
                            shooter,
                            ShootDeniedReason.DEAD
                    )
            );
            return;
        }

        if (shooter.getPlayerState() == PlayerState.WAITING) {
            Bukkit.getPluginManager().callEvent(
                    new BlockShotPlayerShootDeniedEvent(
                            shooter,
                            ShootDeniedReason.NOT_YOUR_TURN
                    )
            );
            return;
        }
        //TODO implementar un sistema para configurar el tiempo antes de ejecutar el shoot
        shoot(shooter, target);
    }

    public void shoot(BlockShotPlayer shooterPlayer, BlockShotPlayer targetPlayer) {

        if (shotGun.isEmpty()) {
            return;
        }

        if (shooterPlayer.getAimTask() != null) {
            shooterPlayer.getAimTask().stop();
        }

        boolean endTurn = true;

        ShellType shell = shotGun.shoot();

        switch (shell) {

            case BLANK -> {
                boolean shotSelf = shooterPlayer.equals(targetPlayer);

                // bala vacía a uno mismo = conserva turno
                if (shotSelf) {
                    endTurn = false;
                }
            }

            case LIVE -> {
                int damage = shotGun.getState() == ShotGunState.SAWED_OFF ? 2 : 1;

                shooterPlayer.setPlayerState(PlayerState.WAITING);

                targetPlayer.takeDamage(shooterPlayer, damage);
            }
        }

        Bukkit.getPluginManager().callEvent(
                new BlockShotPlayerShootEvent(
                        shooterPlayer,
                        targetPlayer,
                        shell
                )
        );

        if (endTurn) {
            Bukkit.getPluginManager().callEvent(
                    new BlockShotTurnEndEvent(
                            this,
                            shooterPlayer
                    )
            );

            turnManager.next();
        }

        processNextTurn();

        if (shotGun.getState() == ShotGunState.SAWED_OFF) {
            shotGun.setState(ShotGunState.NORMAL);
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