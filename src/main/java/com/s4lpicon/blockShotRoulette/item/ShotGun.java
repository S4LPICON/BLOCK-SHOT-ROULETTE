package com.s4lpicon.blockShotRoulette.item;

import com.s4lpicon.blockShotRoulette.event.shotgun.BlockShotShotgunStateChangedEvent;
import com.s4lpicon.blockShotRoulette.item.type.ShellType;
import com.s4lpicon.blockShotRoulette.state.ShotGunState;
import net.md_5.bungee.api.ChatColor;
import org.bukkit.Bukkit;

import java.util.*;

public class ShotGun {

    private final Deque<ShellType> shells = new ArrayDeque<>();
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

        shells.addAll(newShells);
    }
    public boolean isEmpty(){
        return shells.isEmpty();
    }

    public ShellType shoot(){
        //TODO call BlockShotShotgunShootEvent
        return shells.removeFirst();
    }

    public Optional<ShellType> peek(){
        //TODO call BlockShotShotgunPeekEvent
        return Optional.ofNullable(shells.peekFirst());
    }

    public Optional<ShellType> ejectShell() {
        //TODO call BlockShotShotgunShellEjectedEvent
        return Optional.ofNullable(shells.pollFirst());
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
        //TODO call BlockShotShotgunStateChangedEvent
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
