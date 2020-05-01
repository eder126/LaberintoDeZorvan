package CL.stone;

import CL.ataque.Elemento;

/**
 * @author Ed
 * @version 2
 */
public abstract class Stone {
    protected int vidaInicial;
    protected int vida;
    protected Elemento[] elementos;

    /**
     * Funcion que clona el Stone a uno de los prototipos respectivos
     * @param vida = Determina la vida del stone, 100 por un stone del tablero, 60 por un stone creado por personaje Roca.
     * @param elementos = Recibe los elementos escogidos por el jugador oponente.
     * @return Retorna el stone prototipo clona correspondiente.
     */
    public abstract Stone clonar(int vidaInicial, int vida, Elemento[] elementos);

    /**
     *  Funcion que asigna al stone los elementos que tienen ventaja sobre el jugador oponente.
     * @param elementos = Recibe los elementos escogidos por el jugador oponente
     * @return Retorna los elementos que representan una debilidad por cada elemento del jugador oponente.
     */
    public abstract Elemento[] elementosStone(Elemento[] elementos);

    public int getVidaInicial() {
        return vidaInicial;
    }

    public void setVidaInicial(int vidaInicial) {
        this.vidaInicial = vidaInicial;
    }

    public int getVida() {
        return vida;
    }

    public void setVida(int vida) {
        this.vida = vida;
    }

    public Elemento[] getElementos() {
        return elementos;
    }

    public void setElementos(Elemento[] elementos) {
        this.elementos = elementos;
    }

    /**
     *
     * @return String de los datos del Stone.
     */
    public String getData() {
        return "Vida inicial " + getVidaInicial() + "\nVida: " + getVida() + "\nElementos: ";
    }
}
