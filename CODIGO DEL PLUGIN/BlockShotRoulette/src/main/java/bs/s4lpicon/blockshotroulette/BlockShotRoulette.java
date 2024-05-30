package bs.s4lpicon.blockshotroulette;

import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;

public final class BlockShotRoulette extends JavaPlugin {

    @Override
    public void onEnable() {
        // Plugin startup logic
        getLogger().info("Plugin activado");
        Bukkit.getPluginManager().registerEvents(new GameAdmin(), this);

    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
    }
}
