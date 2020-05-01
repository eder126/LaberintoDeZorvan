package CL.tablero;

import CL.misc.Criatura;

public class Casilla {
    private int espacio;
    private Criatura creature;

    public Casilla() {
    }

    public Casilla(int espacio, Criatura creature) {
        this.espacio = espacio;
        this.creature = creature;
    }

    public int calcularEspacio(){
        return -1; //Algo acá
    }

    public int getEspacio() {
        return espacio;
    }

    public void setEspacio(int espacio) {
        this.espacio = espacio;
    }

    public Criatura getCreature() {
        return creature;
    }

    public void setCreature(Criatura creature) {
        this.creature = creature;
    }

    public boolean getEquation(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Casilla casilla = (Casilla) o;
        return espacio == casilla.espacio &&
                creature == casilla.creature;
    }

    public String getData() {
        return "Casilla{" +
                "espacio=" + espacio +
                ", creature=" + creature +
                '}';
    }
}
