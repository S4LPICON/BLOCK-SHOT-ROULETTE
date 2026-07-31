package com.s4lpicon.blockShotRoulette.listener.game.shotgun;

import com.s4lpicon.blockShotRoulette.event.shotgun.shell.BlockShotShotgunShellEjectedEvent;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;

public class BlockShotShotgunShellEjectedListener implements Listener {

    @EventHandler
    public void onShellEjected(BlockShotShotgunShellEjectedEvent event){
        //TODO no se porque pero creo que este evento necesita mas variables para poder manejarse
    }
}