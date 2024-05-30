package bs.s4lpicon.blockshotroulette;

import java.util.ArrayList;
import java.util.Random;

public class Escopeta {
    private int MaxBalas, BalasSalva, BalasLive;
    private ArrayList<Boolean> ordenBalas;

    public Escopeta(int MaxBalas, int BalasLive){
        this.MaxBalas = MaxBalas;
        this.BalasLive = BalasLive;
        this.BalasSalva = (this.MaxBalas - this.BalasLive);
        this.ordenBalas = new ArrayList<>();
    }

    public int getMaxBalas() {
        return MaxBalas;
    }

    public void setMaxBalas(int maxBalas) {
        MaxBalas = maxBalas;
    }

    public int getBalasSalva() {
        return BalasSalva;
    }

    public void setBalasSalva(int balasSalva) {
        BalasSalva = balasSalva;
    }

    public int getBalasLive() {
        return BalasLive;
    }

    public void setBalasLive(int balasLive) {
        BalasLive = balasLive;
    }

    public ArrayList<Boolean> getOrdenBalas() {
        return ordenBalas;
    }

    public void setOrdenBalas(ArrayList<Boolean> ordenBalas) {
        this.ordenBalas = ordenBalas;
    }

    @Override
    public String toString() {
        return "Escopeta{" +
                "MaxBalas=" + MaxBalas +
                ", BalasSalva=" + BalasSalva +
                ", BalasLive=" + BalasLive +
                ", ordenBalas=" + ordenBalas +
                '}';
    }

    public void recargar() {
        int balasVaciasCopy = this.BalasSalva;
        int balasCargadasCopy = this.BalasLive;
        Random random = new Random();
        for (int cont2 = 0; cont2 < this.MaxBalas; cont2++) {
            if (random.nextInt(balasVaciasCopy + balasCargadasCopy) + 1 <= balasVaciasCopy) {
                this.ordenBalas.add(false);
                balasVaciasCopy--;
            } else {
                this.ordenBalas.add(true);
                balasCargadasCopy--;
            }
        }
//        System.out.println("Se recargó la escopeta en un orden aleatorio");
//        System.out.println("El orden de los cartuchos es: " + this.getOrdenBalas());
    }

    public boolean disparar(BSplayer player){
        if (this.ordenBalas.get(0)){
            this.ordenBalas.remove(0);
            player.bajarDesfibrilador(1);
            return true;
        }
        this.ordenBalas.remove(0);
        return false;
    }
}
