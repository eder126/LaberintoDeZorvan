package CL.computadora;

import javafx.event.Event;
import javafx.scene.Node;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;

public class AdaptadorComputadora extends Jugador {
    private Computadora pc;

    public AdaptadorComputadora() {
        super();
        this.pc = new Computadora();
    }

    public AdaptadorComputadora(Computadora pc) {
        super();
        this.pc = pc;
    }

    @Override
    public void jugarAtaques(Node opcion1, Node opcion2, Node opcion3) throws InterruptedException {
        Node[] botones = {opcion1, opcion2, opcion3};
        int eleccion;

        do {
            eleccion = pc.elegir(botones.length);
        } while (botones[eleccion].isDisable());

        oprimirBoton(botones[eleccion]);
    }

    @Override
    public void oprimirBoton(Node boton) {
        Event.fireEvent(boton, new MouseEvent(MouseEvent.MOUSE_CLICKED, 0, 0, 0, 0, MouseButton.PRIMARY,
                1, true, true, true, true, true, true, true, true, true,
                true, null));
    }
}
