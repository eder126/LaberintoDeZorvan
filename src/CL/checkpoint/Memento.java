package CL.checkpoint;

import CL.misc.Ficha;
import CL.tablero.Casilla;
import CL.tablero.Tablero;

public class Memento {
    Ficha[] fichas;
    Casilla[][] casillas;

    public Memento(Casilla[][] casillas, Ficha[] fichas){
        Ficha[] fichasN = new Ficha[fichas.length];
        for(int i = 0; i < fichas.length; i++){
            fichasN[i] = new Ficha(fichas[i].getNombre(), fichas[i].getPersonajes(), fichas[i].getColor(), fichas[i].getComputadora());
        }
        this.setFichas(fichasN);
        this.setCasillas(casillas);
    }

    public Ficha[] getFichas(){
        return this.fichas;
    }

    public void setFichas(Ficha[] fichas){
        this.fichas = fichas;
    }

    public Casilla[][] getCasillas(){
        return this.casillas;
    }

    public void setCasillas(Casilla[][] casillas){
        this.casillas = casillas;
    }
}
