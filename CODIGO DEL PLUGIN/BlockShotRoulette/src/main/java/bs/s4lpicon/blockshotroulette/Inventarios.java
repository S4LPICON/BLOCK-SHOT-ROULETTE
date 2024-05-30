package bs.s4lpicon.blockshotroulette;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

public class Inventarios {

    public void openInv(Player jugador) {
        // Crear un nuevo inventario con 2 espacios
        Inventory inventario = Bukkit.createInventory(null, 9, "Selección de accion");

        // Crear los ítems
        ItemStack itemDispararse = new ItemStack(Material.PRISMARINE_SHARD);
        ItemMeta metaDispararse = itemDispararse.getItemMeta();
        metaDispararse.setDisplayName("dispararse");
        itemDispararse.setItemMeta(metaDispararse);

        ItemStack itemDispararOpo = new ItemStack(Material.PRISMARINE_SHARD);
        ItemMeta metaDispararOpo = itemDispararOpo.getItemMeta();
        metaDispararOpo.setDisplayName("dispararopo");
        itemDispararOpo.setItemMeta(metaDispararOpo);

        // Agregar los ítems al inventario
        inventario.setItem(0, itemDispararse);
        inventario.setItem(1, itemDispararOpo);

        // Abrir el inventario para el jugador
        jugador.openInventory(inventario);
    }

}
