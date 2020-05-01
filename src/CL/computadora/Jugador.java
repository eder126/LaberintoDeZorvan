package CL.computadora;

import javafx.scene.Node;

public abstract class Jugador {
    abstract public void jugarAtaques(Node opcionAtacar, Node opcionActivar, Node opcionTirar) throws InterruptedException;

    abstract public void oprimirBoton(Node boton);
}
