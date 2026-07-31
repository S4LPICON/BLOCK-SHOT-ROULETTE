package com.s4lpicon.blockShotRoulette.model;

import com.s4lpicon.blockShotRoulette.BlockShotRoulette;
import com.s4lpicon.blockShotRoulette.event.EventDispatcher;
import com.s4lpicon.blockShotRoulette.event.match.BlockShotMatchEndEvent;
import com.s4lpicon.blockShotRoulette.event.match.BlockShotMatchStartEvent;
import com.s4lpicon.blockShotRoulette.event.player.BlockShotPlayerDamageEvent;
import com.s4lpicon.blockShotRoulette.event.player.BlockShotPlayerDeathEvent;
import com.s4lpicon.blockShotRoulette.event.player.BlockShotPlayerShootDeniedEvent;
import com.s4lpicon.blockShotRoulette.event.player.BlockShotPlayerShootEvent;
import com.s4lpicon.blockShotRoulette.event.player.model.DamageReason;
import com.s4lpicon.blockShotRoulette.event.player.model.ShootDeniedReason;
import com.s4lpicon.blockShotRoulette.event.round.BlockShotRoundStartEvent;
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
import java.util.List;
import java.util.Optional;

public class BlockShotGame {

    private final List<@NotNull BlockShotPlayer> players = new ArrayList<>(4);
    private final TurnManager turnManager;
    private final ShotGun shotGun;
    private GameState gameState = GameState.WAITING;
    private final ItemManager itemManager;
    private final BlockShotRoulette plugin;


    private final EventDispatcher eventDispatcher;
    //prívate int sequence = 1;
    private int round = 0;


    public BlockShotGame(@NotNull  List<Player> players, BlockShotRoulette plugin, EventDispatcher eventDispatcher) {
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
        this.eventDispatcher = eventDispatcher;
    }

    public void startGame(){
        this.gameState = GameState.STARTING;
        eventDispatcher.call(
                new BlockShotMatchStartEvent(
                        this
                )
        );
        startRound();
    }

    public void endGame(){
        //TODO logica para terminar el juego
        eventDispatcher.call(
                new BlockShotMatchEndEvent(
                        turnManager.getActualTurnPlayer(), //apuesto a que si en la ultima ronda me mato gano todo
                        this
                )
        );
    }

    public void startRound() {
        if (round >= RoundRegistry.ROUNDS.size()){
            endGame();
            return;
        }

        RoundSettings roundSettings = RoundRegistry.ROUNDS.get(round);
        eventDispatcher.call(
                new BlockShotRoundStartEvent(
                        this
                )
        );
        resetPlayerStates(roundSettings);
        startSequence(roundSettings);
    }

    public void resetPlayerStates(RoundSettings roundSettings){
        for (BlockShotPlayer player : players) {
            player.setEnergy(roundSettings.getLives());
            // RESTABLECER ESTADO DE LOS JUGADORES:
            player.setPlayerState(PlayerState.WAITING);
            itemManager.clear(player);

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
            round++;
            startNextRound();
            return;
        }

        if(shotGun.isEmpty()){
            startNextSequence();
            return;
        }

        BlockShotPlayer player = turnManager.getActualTurnPlayer();
        startTurn(player);
    }

    private boolean hasRoundEnded(){

        long alivePlayers = players.stream()
                .filter(player -> player.getPlayerState() != PlayerState.DEAD)
                .count();

        return alivePlayers < 2;
    }

    private void startNextRound(){

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

        eventDispatcher.call(
                new BlockShotTurnStartEvent(
                        this,
                        player
                )
        );

        player.setPlayerState(PlayerState.PLAYING);

    }
    public void handleShoot(BlockShotPlayer shooter, BlockShotPlayer target){

        if (shooter.getPlayerState() == PlayerState.DEAD) {
            eventDispatcher.call(
                    new BlockShotPlayerShootDeniedEvent(
                            shooter,
                            ShootDeniedReason.DEAD
                    )
            );
            return;
        }

        if (shooter.getPlayerState() == PlayerState.WAITING) {
            eventDispatcher.call(
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
                shooterPlayer.setPlayerState(PlayerState.WAITING);
                damagePlayer(shooterPlayer, targetPlayer);

            }
        }

        eventDispatcher.call(
                new BlockShotPlayerShootEvent(
                        shooterPlayer,
                        targetPlayer,
                        shell
                )
        );

        if (endTurn) {
            eventDispatcher.call(
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

    public void damagePlayer(BlockShotPlayer damager, BlockShotPlayer target){

        int amount = shotGun.getState() == ShotGunState.SAWED_OFF ? 2 : 1;

        target.takeDamage(amount);
        eventDispatcher.call(
                new BlockShotPlayerDamageEvent(
                        damager,
                        target,
                        amount
                )
        );

        if (target.getEnergy() <= 0) {

            DamageReason reason = damager == target
                    ? DamageReason.SELF_SHOT
                    : DamageReason.PLAYER_SHOT;

            target.kill();
            eventDispatcher.call(
                    new BlockShotPlayerDeathEvent(
                            target,
                            damager,
                            DamageReason.PLAYER_SHOT
                    )
            );
        }

    }

    public void ejectShell(BlockShotPlayer player) {

        Optional<ShellType> shell = shotGun.ejectFirstShell();

        if (shell.isEmpty()) {
            return;
        }

        if (shotGun.isEmpty()) {
            processNextTurn();
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

    public EventDispatcher getEventDispatcher() {
        return eventDispatcher;
    }
}