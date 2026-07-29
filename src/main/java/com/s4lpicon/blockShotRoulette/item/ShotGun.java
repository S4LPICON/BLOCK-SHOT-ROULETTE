package com.s4lpicon.blockShotRoulette.item;

import net.md_5.bungee.api.ChatColor;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class ShotGun {

    private final List<ShellType> shells ;
    private boolean sawedOff;

    public ShotGun(int liveShells, int blankShells){
        this.shells = reloadShotGun(liveShells, blankShells);
    }

    public List<ShellType> reloadShotGun(int liveShells, int blankShells) {
        List<ShellType> shotgun = new ArrayList<>(liveShells + blankShells);

        for (int i = 0; i < liveShells; i++) {
            shotgun.add(ShellType.LIVE);
        }

        for (int i = 0; i < blankShells; i++) {
            shotgun.add(ShellType.BLANK);
        }

        Collections.shuffle(shotgun);

        return shotgun;
    }
    public boolean isSawedOff() {
        return sawedOff;
    }
    public boolean isEmpty(){
        return shells.isEmpty();
    }

    public void setSawedOff(boolean sawedOff) {
        this.sawedOff = sawedOff;
    }

    public ShellType shoot(){
        ShellType shell = shells.getFirst();
        shells.removeFirst();
        return shell;

    }

    public ShellType peek(){
        return this.shells.getLast();
    }

    public ShellType removeFirst(){
        return this.shells.removeFirst();
    }

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

}
