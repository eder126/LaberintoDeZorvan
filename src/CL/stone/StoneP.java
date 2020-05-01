package CL.stone;

import CL.ataque.Elemento;

import java.util.Random;

import static CL.ataque.Elemento.*;

/**
 * @author Ed
 * @version 2
 */
public class StoneP extends Stone {

    /**
     * Constructor unico de Stone_Cien. Atributo de vida se asigna directamente en el cuerpo.
     * @param vida = Determina la vida del stone, 100 por un stone del tablero, 60 por un stone creado por personaje Roca.
     * @param elementos = Asigna los 3 elementos en base a las debilidades del jugador oponente
     */
    public StoneP(int vidaInicial, int vida, Elemento[] elementos) {
        this.setVidaInicial(vidaInicial);
        this.setVida(vida);
        this.setElementos(elementosStone(elementos));
    }

    /**
     *  Funcion que asigna al stone los elementos que tienen ventaja sobre el jugador oponente.
     * @param elementos = Recibe los elementos escogidos por el jugador oponente
     * @return Retorna los elementos que representan una debilidad por cada elemento del jugador oponente.
     */
    public Elemento[] elementosStone(Elemento[] elementos) {
        Elemento[] nElems = new Elemento[3];
        int i = 0;
        do{
            boolean resetear = false;
            switch (elementos[i]) {
                case FUEGO:
                    /*AGUA,ELECTRICO,ROCA*/
                    switch (new Random().nextInt((3 - 1) + 1) + 1) {
                        case 1:
                            if(nElems[0] != AGUA && nElems[1] != AGUA && nElems[2] != AGUA) {
                                nElems[i] = AGUA;
                                i++;
                            }
                            break;
                        case 2:
                            if(nElems[0] != ELECTRICO && nElems[1] != ELECTRICO && nElems[2] != ELECTRICO) {
                                nElems[i] = ELECTRICO;
                                i++;
                            }
                            break;
                        case 3:
                            if(nElems[0] != ROCA && nElems[1] != ROCA && nElems[2] != ROCA) {
                                nElems[i] = ROCA;
                                i++;
                            }
                            break;
                    }
                    break;
                case AGUA:
                    /*PLANTA,ELECTRICO,HIELO*/
                    switch (new Random().nextInt((3 - 1) + 1) + 1) {
                        case 1:
                            if(nElems[0] != PLANTA && nElems[1] != PLANTA && nElems[2] != PLANTA) {
                                nElems[i] = PLANTA;
                                i++;
                            }
                            break;
                        case 2:
                            if(nElems[0] != ELECTRICO && nElems[1] != ELECTRICO && nElems[2] != ELECTRICO) {
                                nElems[i] = ELECTRICO;
                                i++;
                            }
                            break;
                        case 3:
                            if(nElems[0] != HIELO && nElems[1] != HIELO && nElems[2] != HIELO) {
                                nElems[i] = HIELO;
                                i++;
                            }
                            break;
                    }
                    break;
                case PLANTA:
                    /*FUEGO,ROCA,HIELO*/
                    switch (new Random().nextInt((3 - 1) + 1) + 1) {
                        case 1:
                            if(nElems[0] != FUEGO && nElems[1] != FUEGO && nElems[2] != FUEGO) {
                                nElems[i] = FUEGO;
                                i++;
                            }
                            break;
                        case 2:
                            if(nElems[0] != ROCA && nElems[1] != ROCA && nElems[2] != ROCA) {
                                nElems[i] = ROCA;
                                i++;
                            }
                            break;
                        case 3:
                            if(nElems[0] != HIELO && nElems[1] != HIELO && nElems[2] != HIELO) {
                                nElems[i] = HIELO;
                                i++;
                            }
                            break;
                    }
                    break;
                case ELECTRICO:
                    /*PLANTA,ROCA*/
                    switch (new Random().nextInt((2 - 1) + 1) + 1) {
                        case 1:
                            if(nElems[0] != PLANTA && nElems[1] != PLANTA && nElems[2] != PLANTA) {
                                nElems[i] = PLANTA;
                                i++;
                            }
                            else if(nElems[1] == PLANTA && nElems[0] == ROCA)
                                resetear = true;
                            break;
                        case 2:
                            if(nElems[0] != ROCA && nElems[1] != ROCA && nElems[2] != ROCA) {
                                nElems[i] = ROCA;
                                i++;
                            }
                            else if(nElems[1] == ROCA && nElems[0] == PLANTA)
                                resetear = true;
                            break;
                    }
                    break;
                case ROCA:
                    /*AGUA,HIELO*/
                    switch (new Random().nextInt((2 - 1) + 1) + 1) {
                        case 1:
                            if(nElems[0] != AGUA && nElems[1] != AGUA && nElems[2] != AGUA) {
                                nElems[i] = AGUA;
                                i++;
                            }
                            else if(nElems[1] == AGUA && nElems[0] == HIELO)
                                resetear = true;
                            break;
                        case 2:
                            if(nElems[0] != HIELO && nElems[1] != HIELO && nElems[2] != HIELO) {
                                nElems[i] = HIELO;
                                i++;
                            }
                            else if(nElems[1] == HIELO && nElems[0] == AGUA)
                                resetear = true;
                            break;
                    }
                    break;
                case HIELO:
                    /*FUEGO,ELECTRICO*/
                    switch (new Random().nextInt((2 - 1) + 1) + 1) {
                        case 1:
                            if(nElems[0] != FUEGO && nElems[1] != FUEGO && nElems[2] != FUEGO) {
                                nElems[i] = FUEGO;
                                i++;
                            }
                            else if(nElems[1] == FUEGO && nElems[0] == ELECTRICO)
                                resetear = true;
                            break;
                        case 2:
                            if(nElems[0] != ELECTRICO && nElems[1] != ELECTRICO && nElems[2] != ELECTRICO) {
                                nElems[i] = ELECTRICO;
                                i++;
                            }
                            else if(nElems[1] == ELECTRICO && nElems[0] == FUEGO)
                                resetear = true;
                            break;
                    }
                    break;
            }
            if(resetear)
                i=0;
        } while(i < 3);

        return nElems;
    }

    /**
     *  Funcion que clona el Stone a uno de los prototipos respectivos
     * @param vida = Determina la vida del stone, 100 por un stone del tablero, 60 por un stone creado por personaje Roca.
     * @param elementos = Recibe los elementos escogidos por el jugador oponente.
     * @return Retorna el stone prototipo clona correspondiente.
     */
    @Override
    public Stone clonar(int vidaInicial, int vida, Elemento[] elementos){
        return new StoneP(vidaInicial, vida, elementos);
    }
}