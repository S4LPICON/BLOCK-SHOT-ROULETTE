package com.s4lpicon.blockShotRoulette.model;

import com.s4lpicon.blockShotRoulette.BlockShotRoulette;
import com.s4lpicon.blockShotRoulette.event.EventDispatcher;
import com.s4lpicon.blockShotRoulette.event.match.BlockShotMatchEndEvent;
import com.s4lpicon.blockShotRoulette.event.match.BlockShotMatchStartEvent;
import com.s4lpicon.blockShotRoulette.event.player.*;
import com.s4lpicon.blockShotRoulette.event.player.model.DamageReason;
import com.s4lpicon.blockShotRoulette.event.player.model.ShootDeniedReason;
import com.s4lpicon.blockShotRoulette.event.round.BlockShotRoundEndEvent;
import com.s4lpicon.blockShotRoulette.event.round.BlockShotRoundStartEvent;
import com.s4lpicon.blockShotRoulette.event.shotgun.BlockShotShotgunStateChangedEvent;
import com.s4lpicon.blockShotRoulette.event.turn.BlockShotTurnEndEvent;
import com.s4lpicon.blockShotRoulette.event.turn.BlockShotTurnStartEvent;
import com.s4lpicon.blockShotRoulette.item.ShotGun;
import com.s4lpicon.blockShotRoulette.item.type.ShellType;
import com.s4lpicon.blockShotRoulette.manager.ItemManager;
import com.s4lpicon.blockShotRoulette.manager.TurnManager;
import com.s4lpicon.blockShotRoulette.registry.RoundRegistry;
import com.s4lpicon.blockShotRoulette.registry.settings.RoundSettings;
import com.s4lpicon.blockShotRoulette.state.GameState;
import com.s4lpicon.blockShotRoulette.state.PlayerState;
import com.s4lpicon.blockShotRoulette.state.ShotGunState;
import com.s4lpicon.blockShotRoulette.task.AimPlayerTask;
import net.kyori.adventure.text.Component;
import org.bukkit.Bukkit;
import org.bukkit.GameMode;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;

public class BlockShotGame {

    private final List<@NotNull BlockShotPlayer> players = new ArrayList<>(4);
    private final ShotGun shotGun;
    private GameState gameState = GameState.WAITING;
    private final ItemManager itemManager;
    private final BlockShotRoulette plugin;
    private final EventDispatcher eventDispatcher;
    private final TurnManager turnManager;
    private int round = 0;

    public BlockShotGame(List<Player> players, EventDispatcher eventDispatcher, BlockShotRoulette plugin) {
        for (Player player : players) {
            this.players.add(new BlockShotPlayer(player, this));
        }

        this.shotGun = new ShotGun(10);
        this.plugin = plugin;
        this.itemManager = plugin.getItemManager();
        this.eventDispatcher = eventDispatcher;
        this.turnManager = new TurnManager(this.players);
    }

    public void startGame() {
        this.gameState = GameState.STARTING;
        eventDispatcher.call(new BlockShotMatchStartEvent(this));
        startRound(this.round);
    }

    public void startRound(int roundIndex) {
        if (roundIndex >= RoundRegistry.ROUNDS.size()) {
            endGame();
            return;
        }

        this.round = roundIndex;
        itemManager.clearItemsFromAll(players); // Asegúrate de limpiar los viejos
        RoundSettings settings = RoundRegistry.ROUNDS.get(roundIndex);

        eventDispatcher.call(new BlockShotRoundStartEvent(this));
        setPlayerLives(settings);
        reloadSequence(settings);
        BlockShotPlayer firstPlayer = turnManager.getActualTurnPlayer();
        if (firstPlayer != null) {
            startTurn(firstPlayer);
        }
        // Arrancamos el primer turno de la ronda

    }

    private void reloadSequence(RoundSettings settings) {
        Bukkit.broadcast(Component.text("¡Cargador vacío! Nuevos ítems y recarga en mesa."));

        // 1. Limpiar e invocar la entrega de nuevos ítems a los jugadores vivos
        giveItemsToAllPlayers(settings);

        // 2. Recargar la escopeta limpiando el cargador anterior
        shotGun.reload(settings.getLiveShells(), settings.getBlankShells());
    }

    private void setPlayerLives(RoundSettings settings){
        for (BlockShotPlayer player : getPlayers()){
            player.setEnergy(settings.getLives());
            if (player.getPlayerState() == PlayerState.DEAD){
                player.getPlayer().setGameMode(GameMode.ADVENTURE);
                player.setPlayerState(PlayerState.WAITING);
            }
            eventDispatcher.call(new BlockShotPlayerHealEvent(player, settings.getLives()));
        }
    }

    public void startTurn(BlockShotPlayer player) {
        // Aquí integras la lógica limpia de la tarea de puntería y el evento de inicio de turno
        eventDispatcher.call(new BlockShotTurnStartEvent(this, player));
        AimPlayerTask task = new AimPlayerTask(player.getPlayer());

        task.runTaskTimer(plugin, 0L, 1L); // <--- Esto es vital (cada 1 tick)
        player.setAimTask(task);
    }

    private void reloadShotgun(RoundSettings settings) {
        shotGun.reload(settings.getLiveShells(), settings.getBlankShells());
    }

    private void giveItemsToAllPlayers(RoundSettings settings) {
        itemManager.giveItemsToAll(players, settings);
    }

    public void handleShoot(BlockShotPlayer shooter, BlockShotPlayer target) {
        // 1. Validar al Tirador
        if (shooter.getPlayerState() == PlayerState.DEAD) {
            eventDispatcher.call(
                    new BlockShotPlayerShootDeniedEvent(shooter, ShootDeniedReason.DEAD)
            );
            return;
        }

        // 2. Validar el Turno
        if (!turnManager.isCurrentPlayer(shooter)) {
            eventDispatcher.call(
                    new BlockShotPlayerShootDeniedEvent(shooter, ShootDeniedReason.NOT_YOUR_TURN)
            );
            return;
        }

        // 3. Validar al Objetivo (EL PUNTO CIEGO CORREGIDO)
        if (target.getPlayerState() == PlayerState.DEAD) {
            // Puedes crear un ShootDeniedReason.TARGET_DEAD si quieres registrarlo
            return;
        }

        // 4. Prevenir Spameo de Paquetes / Doble Disparo (Antirrebote)
        // Si no bloqueas esto, 2 clics rápidos en el mismo tick disparan 2 balas.
        if (shooter.isActionLocked()) {
            return;
        }
        shooter.setActionLocked(true); // Debes desbloquearlo al final de la lógica de shoot() o cambio de turno.

        // Ejecutar lógica pesada
        shoot(shooter, target);
    }
    private long playersAlive() {
        return players.stream()
                .filter(player -> player.getPlayerState() != PlayerState.DEAD)
                .count();
    }

    private BlockShotPlayer getRoundWinner(){
        return players.stream()
                .filter(p -> p.getPlayerState() != PlayerState.DEAD)
                .findFirst()
                .orElse(null);
    }

    private void shoot(BlockShotPlayer shooterPlayer, BlockShotPlayer targetPlayer) {
        if (shotGun.isEmpty()) {
            return;
        }

        // 1. Detener la tarea de apuntado si existe
        if (shooterPlayer.getAimTask() != null) {
            shooterPlayer.getAimTask().stop();
            shooterPlayer.setAimTask(null);
        }

        // 2. Extraer la bala del cargador
        ShellType shell = shotGun.shoot();
        boolean endTurn = true;
        boolean roundEndedByKill = false; // Bandera para saber si la ronda terminó aquí

        // Notificar al exterior que el disparo ocurrió
        eventDispatcher.call(new BlockShotPlayerShootEvent(shooterPlayer, targetPlayer, shell));

        switch (shell) {
            case BLANK -> {
                if (shooterPlayer.equals(targetPlayer)) {
                    endTurn = false;
                }
            }
            case LIVE -> {
                damagePlayer(shooterPlayer, targetPlayer);
                if (playersAlive() == 1) {
                    roundEndedByKill = true;
                    eventDispatcher.call(new BlockShotRoundEndEvent(this, getRoundWinner()));
                    round++;
                    startRound(round); // Inicia la nueva ronda directamente
                }
            }
        }

        if (shooterPlayer.getPlayerState() == PlayerState.DEAD) {
            endTurn = true;
        }

        // Si la ronda terminó por muerte, no ejecutamos la tarea normal de transición de turno de esta ronda
        if (roundEndedByKill) {
            return;
        }

        // 3. SECUENCIA DE SALIDA Y SIGUIENTE TURNO
        long delayBeforeNextTurn = 20L;
        boolean finalEndTurn = endTurn;

        Bukkit.getScheduler().runTaskLater(plugin, () -> {
            if (shooterPlayer.getPlayerState() != PlayerState.DEAD) {
                shooterPlayer.setPlayerState(PlayerState.WAITING);
            }

            // 1. EVALUAR ESTADO DEL ARMA INDEPENDIENTEMENTE DEL TURNO
            if (shotGun.isEmpty()) {
                reloadSequence(RoundRegistry.ROUNDS.get(round));
            }

            // 2. EVALUAR EL FLUJO DE TURNOS
            if (finalEndTurn) {
                eventDispatcher.call(new BlockShotTurnEndEvent(this, shooterPlayer));

                BlockShotPlayer nextPlayer = turnManager.next();

                if (nextPlayer != null) {
                    startTurn(nextPlayer);
                } else {
                    checkGameFlow();
                }
            } else {
                // Si se disparó una salva, repite turno (y si era la última, ya recargó arriba)
                startTurn(shooterPlayer);
            }

            if (shotGun.getState() == ShotGunState.SAWED_OFF) {
                shotGun.setState(ShotGunState.NORMAL);
                eventDispatcher.call(new BlockShotShotgunStateChangedEvent(shotGun, ShotGunState.SAWED_OFF, ShotGunState.NORMAL, this));
            }
        }, delayBeforeNextTurn);
    }

    private void damagePlayer(BlockShotPlayer shooterPlayer, BlockShotPlayer targetPlayer){
        int cantDamage = shotGun.getState() == ShotGunState.SAWED_OFF ? 2 : 1;
        targetPlayer.takeDamage(cantDamage);
        eventDispatcher.call(new BlockShotPlayerDamageEvent(shooterPlayer, targetPlayer, cantDamage));
        if (targetPlayer.getEnergy() <= 0){
            targetPlayer.kill();
            eventDispatcher.call(new BlockShotPlayerDeathEvent(targetPlayer, shooterPlayer, DamageReason.PLAYER_SHOT));
        }
    }

    private void checkGameFlow(){
        Bukkit.getLogger().info("ENTRA A CHEQUEAR EL GAMEFLOW");
    }

    public void endGame() {

        eventDispatcher.call(new BlockShotMatchEndEvent(getRoundWinner(), this));
        // Lógica de finalización
        this.gameState = GameState.ENDED;
    }

    public ShotGun getShotGun(){
        return this.shotGun;
    }

    public EventDispatcher getEventDispatcher(){
        return this.eventDispatcher;
    }

    public int getRound(){
        return this.round;
    }

    public List<@NotNull BlockShotPlayer> getPlayers() {
        return this.players;
    }
}