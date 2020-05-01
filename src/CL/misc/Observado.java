package CL.misc;

import UI.Controller.TableroController;
import java.io.Serializable;

public class Observado implements Observador{
    private Ficha jugador;
    private TableroController tC;

    public Observado(Ficha jugador, TableroController tC) {
        this.jugador = jugador;
        this.tC = tC;
    }

    public void update(Serializable value) {
        mostrarPantalla();
    }

    private void mostrarPantalla() {
        tC.Gano(jugador);
    }


}