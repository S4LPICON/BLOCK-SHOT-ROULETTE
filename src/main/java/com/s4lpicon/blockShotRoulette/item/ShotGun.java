package com.s4lpicon.blockShotRoulette.item;

import com.s4lpicon.blockShotRoulette.event.shotgun.shell.BlockShotShotgunShellEjectedEvent;
import com.s4lpicon.blockShotRoulette.event.shotgun.BlockShotShotgunStateChangedEvent;
import com.s4lpicon.blockShotRoulette.item.type.ShellType;
import com.s4lpicon.blockShotRoulette.state.ShotGunState;
import net.md_5.bungee.api.ChatColor;
import org.bukkit.Bukkit;

import java.util.*;
import java.util.concurrent.ThreadLocalRandom;

public class ShotGun {

    private final Deque<ShellType> shells = new ArrayDeque<>();
    private List<ShellType> initialShells = List.of();
    private ShotGunState state = ShotGunState.NORMAL;
    private final int maxShells;

    public ShotGun(int maxShells) {
        this.maxShells = maxShells;
    }

    public void reload(int liveShells, int blankShells) {

        if (liveShells + blankShells > maxShells) {
            throw new IllegalArgumentException("Too many shells");
        }
        //TODO call BlockShotShotgunReloadEvent

        shells.clear();

        List<ShellType> newShells = new ArrayList<>();

        for (int i = 0; i < liveShells; i++) {
            newShells.add(ShellType.LIVE);
        }

        for (int i = 0; i < blankShells; i++) {
            newShells.add(ShellType.BLANK);
        }

        Collections.shuffle(newShells);
        initialShells = List.copyOf(newShells);

        shells.addAll(newShells);
    }
    public boolean isEmpty(){
        return shells.isEmpty();
    }

    public ShellType shoot(){
        //TODO call BlockShotShotgunShootEvent
        ShellType shell = shells.removeFirst();
        return shell;
    }

    public Optional<ShellType> peek(){
        //TODO call BlockShotShotgunPeekEvent
        return Optional.ofNullable(shells.peekFirst());
    }

    public Optional<ShellType> ejectFirstShell() {

        ShellType shellType = shells.pollFirst();

        if (shellType == null) {
            return Optional.empty();
        }

        Bukkit.getPluginManager().callEvent(
                new BlockShotShotgunShellEjectedEvent(
                        this,
                        shellType
                )
        );

        return Optional.of(shellType);
    }

    public void invertNextShell() {
        if (shells.isEmpty()) {
            return;
        }
        ShellType current = shells.removeFirst();
        ShellType inverted = current == ShellType.LIVE
                ? ShellType.BLANK
                : ShellType.LIVE;

        shells.addFirst(inverted);

        int index = getConsumedShellCount();

        if (index < initialShells.size()) {

            List<ShellType> updated = new ArrayList<>(initialShells);

            updated.set(index, inverted);

            initialShells = List.copyOf(updated);
        }
    }

    public int getRemainingShellCount() {
        return shells.size();
    }

    public int getConsumedShellCount() {
        return initialShells.size() - shells.size();
    }

    public record ShellInfo(
            int originalIndex,
            ShellType shellType
    ) {}

    public Optional<ShellInfo> getRandomFutureShell() {

        if (shells.size() <= 1) {
            return Optional.empty();
        }

        int consumed = getConsumedShellCount();

        // Saltamos la bala actual.
        int firstPossibleIndex = consumed + 1;

        if (firstPossibleIndex >= initialShells.size()) {
            return Optional.empty();
        }

        int randomIndex = ThreadLocalRandom.current()
                .nextInt(firstPossibleIndex, initialShells.size());

        return Optional.of(
                new ShellInfo(
                        randomIndex,
                        initialShells.get(randomIndex)
                )
        );
    }

    @Deprecated(forRemoval = true)
    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder();

        for (ShellType shell : shells) {
            if (shell == ShellType.LIVE) {
                builder.append(ChatColor.RED).append("█");
            } else {
                builder.append(ChatColor.BLUE).append("█");
            }
        }

        return builder.toString();
    }

    public ShotGunState getState() {
        return state;
    }

    public void setState(ShotGunState newState) {
        if (this.state == newState) {
            return;
        }

        ShotGunState oldState = this.state;
        this.state = newState;

        Bukkit.getPluginManager().callEvent(
                new BlockShotShotgunStateChangedEvent(
                        this,
                        oldState,
                        newState
                )
        );
    }
}
