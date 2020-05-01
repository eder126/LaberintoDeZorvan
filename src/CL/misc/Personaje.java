package CL.misc;

import CL.ataque.Elemento;

import java.util.Random;

/**
 * @author Ed
 * @version 1
 */
public class Personaje {
    private int contPtsExtra;
    private int conPoder;
    private Elemento elemento;

    /**
     *  Constructor base de Personaje, de no enviar el elemento seleccionado, se asigna uno aleatoriamente. (Dispueto a cambio)
     */
    public Personaje() {
        this.setContPtsExtra(0);
        this.setConPoder(0);
        int rand = new Random().nextInt(5 + 1);
        Elemento elem = rand == 1 ? Elemento.FUEGO : rand == 2 ? Elemento.AGUA : rand == 3 ? Elemento.PLANTA : rand == 4 ? Elemento.ELECTRICO : rand == 5 ? Elemento.ROCA : Elemento.HIELO;
        this.setElemento(elem);
    }

    /**
     *
     * @param element = Elemento de Personaje. Determina el poder a utilizar.
     */
    public Personaje(Elemento element) {
        this.setContPtsExtra(0);
        this.setConPoder(0);
        this.setElemento(element);
    }

    public int getContPtsExtra() {
        return contPtsExtra;
    }

    public void setContPtsExtra(int contPtsExtra) {
        this.contPtsExtra = contPtsExtra;
    }

    public int getConPoder() {
        return conPoder;
    }

    public void setConPoder(int conPoder) {
        this.conPoder = conPoder;
    }

    public Elemento getElemento() {
        return elemento;
    }

    public void setElemento(Elemento elemento) {
        this.elemento = elemento;
    }

    public void ReducirContadores() {
        if(getConPoder() > 0)
            setConPoder(getConPoder() - 1);
        if(getContPtsExtra() > 0)
            setContPtsExtra(getContPtsExtra() - 1);
    }
}
