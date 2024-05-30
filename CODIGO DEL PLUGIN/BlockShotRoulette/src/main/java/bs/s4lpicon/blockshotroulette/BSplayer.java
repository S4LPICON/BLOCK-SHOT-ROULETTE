package bs.s4lpicon.blockshotroulette;

import org.bukkit.entity.Player;

public class BSplayer{
    private String nombre;
    private int desfibriladores, cigarrillos, lupas, cuchillos, cervezas, esposas;


    public BSplayer(String nombre){
        this.nombre = nombre;
        this.desfibriladores = 0;
        this.cigarrillos = 0;
        this.lupas = 0;
        this.cuchillos = 0;
        this.cervezas = 0;
        this.esposas = 0;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public int getDesfibriladores() {
        return desfibriladores;
    }

    public void setDesfibriladores(int desfibriladores) {
        this.desfibriladores = desfibriladores;
    }

    public int getCigarrillos() {
        return cigarrillos;
    }

    public void setCigarrillos(int cigarrillos) {
        this.cigarrillos = cigarrillos;
    }

    public int getLupas() {
        return lupas;
    }

    public void setLupas(int lupas) {
        this.lupas = lupas;
    }

    public int getCuchillos() {
        return cuchillos;
    }

    public void setCuchillos(int cuchillos) {
        this.cuchillos = cuchillos;
    }

    public int getCervezas() {
        return cervezas;
    }

    public void setCervezas(int cervezas) {
        this.cervezas = cervezas;
    }

    public int getEsposas() {
        return esposas;
    }

    public void setEsposas(int esposas) {
        this.esposas = esposas;
    }

    @Override
    public String toString() {
        return "Jugador{ " + nombre + '\'' +
                ", desfibriladores=" + desfibriladores +
                ", cigarrillos=" + cigarrillos +
                ", lupas=" + lupas +
                ", cuchillos=" + cuchillos +
                ", cervezas=" + cervezas +
                ", esposas=" + esposas +
                '}';
    }

    public void bajarDesfibrilador(int cantidad){
        this.desfibriladores -= cantidad;
    }
}
