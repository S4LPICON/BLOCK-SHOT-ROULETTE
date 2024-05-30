package bs.s4lpicon.blockshotroulette;

import org.bukkit.*;
import org.bukkit.block.Block;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Minecart;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.event.vehicle.VehicleExitEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

import java.util.ArrayList;
import java.util.List;

public class GameAdmin implements Listener {
    //variables de ronda NO TOCAR
    private int ronda;
    Inventarios inventarios = new Inventarios();
    Escopeta escopeta;
    private int jugador_actual = 0;
    private boolean winner;
    private final ArrayList<Player> playersInMinecarts = new ArrayList<>();
    private final ArrayList<BSplayer> players = new ArrayList<>();;
    //--------------------------------

    // Coordenadas x y z del spawn del minijuego (los jugadores al salir del asiento son tpados aqui)
    private double spwx = 21.5, spwy = -59, spwz = 20.5;
    //------------------------------------------------

    //Coordenadas de los bloques de entrada x1 y1 z1 para jugador 1 y su tipo de bloque
    private static final Material BloqueDeEntrada = Material.END_PORTAL_FRAME;
    private int Entrancex1 = 19, Entrancey1 = -60, Entrancez1 = 15, Entrancex2 = 23, Entrancey2 = -60, Entrancez2 = 15;
    //---------------------------------------------

    //coordenadas de los asientos (donde los jugadores estaran sentados)
    private double ubiAsientox1 = 10.5, ubiAsientoy1 = -59, ubiAsientoz1 = 23.5, ubiAsientox2 = 10.5, ubiAsientoy2 = -59, ubiAsientoz2 = 17.5;
    //-----------------------------------------------------

    public String inciarJuego(Location ubi0, ArrayList<BSplayer> players){
        ronda1(players);
        return "";
    }


    public String ronda1(ArrayList<BSplayer> players) {
        int maxbalas = 3, balaslive = 1;
        players.get(0).setDesfibriladores(2);
        players.get(1).setDesfibriladores(2);
        Player jugador = Bukkit.getPlayer(players.get(0).getNombre());
        Player jugador2 = Bukkit.getPlayer(players.get(1).getNombre());
        escopeta.setMaxBalas(maxbalas);
        escopeta.setBalasLive(balaslive);
        escopeta.recargar();
        inventarios.openInv(jugador);
        //necesito saber que item pulso
        // para hacer un players.get(player_actual).disparar();



        return "";
    }

    public String ronda2(){
        return "";
    }

    public String ronda3(){
        return "";
    }




//Funciones para la ejecucion de las rondas

    public int aQuienLeToca(){
        return 0;
    }

    public void seDisparoAsiMismo(){

    }


//---------------------------------------------------------------
//metodos de control para la entrada de los jugadores al juego
    private void checkAndStartGame() {
        //LOGICA PARA MPEZAR EL JUEGO
        if (playersInMinecarts.size() >= 2) {
            //mensaje de ijnicio de juego a ambos jugadores
            for (Player player : playersInMinecarts) {
                player.sendMessage("¡Que comience el juego!");
            }
            //aui va la logica del juego
            //ubicacion de la mitad de la mesa
            Location location = new Location(Bukkit.getWorld("world"), 0, 64, 0);
            BSplayer p1 = new BSplayer(playersInMinecarts.get(0).getDisplayName());
            BSplayer p2 = new BSplayer(playersInMinecarts.get(1).getDisplayName());
            players.add(p1);
            players.add(p2);
            inciarJuego(location, players);

        }
    }
    @EventHandler
    public void EntradaDeJugadores(PlayerMoveEvent event) {
        //ADMINISTRADOR DE ENTRADA DE JUGADORES
        Player player = event.getPlayer();
        Location location = player.getLocation();
        World world = player.getWorld();
        //SE VERIFICA QUE ESTE PARADO EN LOS BLOQUES CORRECTOS
        if ((location.getBlockX() == Entrancex1 && location.getBlockY() == Entrancey1 && location.getBlockZ() == Entrancez1) ||
                (location.getBlockX() == Entrancex2 && location.getBlockY() == Entrancey2 && location.getBlockZ() == Entrancez2)) {
            //SE VERIFICA EL TIPO DE BLOQUE
            if (location.getBlock().getType() == BloqueDeEntrada) {
                player.playSound(player.getLocation(), Sound.ENTITY_VILLAGER_YES, 1.0f, 1.0f);

                //logica de entrar al juego player 1
                if (location.getBlockX() == Entrancex1) {
                    player.sendMessage("¡Haz Entrado al juego 1p!");
                    tpVagonetaJugador(player, world, 1);

                //logica de entrar al juego player 2
                } else if (location.getBlockX() == Entrancex2) {
                    player.sendMessage("¡Haz Entrado al juego 2p!");
                    tpVagonetaJugador(player, world, 2);
                }
            }
        }
    }
    private void tpVagonetaJugador(Player player, World world, int juga) {
        //Tpar al jugador a su asiento
        double x = juga == 1 ? ubiAsientox1 : ubiAsientox2;
        double y = juga == 1 ? ubiAsientoy1 : ubiAsientoy2;
        double z = juga == 1 ? ubiAsientoz1 : ubiAsientoz2;

        Location location = new Location(world, x, y, z);
        player.teleport(location);

        // Generar una vagoneta en la ubicación específica
        Minecart minecart = (Minecart) location.getWorld().spawnEntity(location, EntityType.MINECART);

        // Montar al jugador en la vagoneta
        minecart.addPassenger(player);
        playersInMinecarts.add(player);
        checkAndStartGame();
    }
    @EventHandler
    public void onVehicleExit(VehicleExitEvent event) {
        //LOGICA PARA CUANDO EL JUGADOR SALE DE UNA VAGONETA TPARLO AL SPAWN
        if (event.getExited() instanceof Player && event.getVehicle() instanceof Minecart) {
            Player player = (Player) event.getExited();
            World world = player.getWorld();
            playersInMinecarts.remove(player);
            Location teleportLocation = new Location(world, spwx, spwy, spwz);
            removeMinecartAt(player.getLocation());
            player.teleport(teleportLocation);
            player.sendMessage("¡Haz Salido del juego!");
        }
    }
    public void removeMinecartAt(Location location) {
        //LOGICA PARA ELIMINAR LA VAGONETA DONDE ESTA SENTADO EL JUGADOR AL SALIR DE ESTA
        List<Entity> entities = location.getWorld().getEntities();
        for (Entity entity : entities) {
            if (entity.getType() == EntityType.MINECART) {
                Location entityLocation = entity.getLocation();
                if (entityLocation.getBlockX() == location.getBlockX() &&
                        entityLocation.getBlockY() == location.getBlockY() &&
                        entityLocation.getBlockZ() == location.getBlockZ()) {
                    entity.remove();
                    break;
                }
            }
        }
    }
//-----------------------------------------------------------
// Administrador de click del inventario principal
@EventHandler
public void onInventoryClick(InventoryClickEvent event) {
    if (!event.getView().getTitle().equals("Selección de accion")) {
        return;
    }

    if (event.getCurrentItem() == null || event.getCurrentItem().getItemMeta() == null) {
        return;
    }

    ItemStack itemClickeado = event.getCurrentItem();
    String nombreItem = itemClickeado.getItemMeta().getDisplayName();

    Player jugador = (Player) event.getWhoClicked();
    event.setCancelled(true);
    jugador.closeInventory();

    if (nombreItem.equals("dispararse")) {
        jugador.sendMessage("¡Elegiste dispararte!");
        if (jugador.equals(jugador_actual)) {
//            this.players.get(0).disparar();
            this.escopeta.disparar(players.get(0));
        } else {
            this.escopeta.disparar(players.get(1));
        }
    } else if (nombreItem.equals("dispararopo")) {
        jugador.sendMessage("¡Elegiste dispararopo!");
        if (jugador.equals(jugador_actual)) {
            this.escopeta.disparar(players.get(1));
        } else {
            this.escopeta.disparar(players.get(0));
        }
    }
}
//-----------------------------------------------------------
// Metodos clasicos para manipular informacion
    public int getRonda() {
        return ronda;
    }
    public void setRonda(int ronda) {
        this.ronda = ronda;
    }
    public boolean isWinner() {
        return winner;
    }
    public void setWinner(boolean winner) {
        this.winner = winner;
    }
//---------------------------------------------------------------------

}
