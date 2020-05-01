package CL.ataque;

import CL.misc.Ficha;
import CL.misc.Personaje;
import CL.tablero.Tablero;

import java.util.ArrayList;
import java.util.Random;

public class Poder {
    private Elemento element;
    private Personaje character;
    private Ficha token;
    private Tablero board;
    private boolean activado;

    public Poder() {
        element = Elemento.values()[new Random().nextInt(Elemento.values().length)];
        character = new Personaje();
        token = new Ficha();
        board = Tablero.getTablero();
    }

    public Poder(Elemento element, Personaje character) {
        this.element = element;
        this.character = character;
        token = new Ficha();
        board = Tablero.getTablero();
    }

    public Poder(Elemento element, Ficha token) {
        this.element = element;
        this.token = token;
        character = new Personaje();
        board = Tablero.getTablero();
    }

    public Poder(Elemento element, Ficha token, Tablero board) {
        this.element = element;
        this.token = token;
        this.board = board;
        character = new Personaje();
    }

    public boolean activar() {
        activado = true;

        switch (element) {
            case FUEGO:
                character.setContPtsExtra(character.getContPtsExtra() + 2);
                break;
            case AGUA:
                character.setConPoder(3);
                break;
            case PLANTA:
                character.setConPoder(3);
                token.setContDado(token.getContDado() + 2);
                break;
            case ELECTRICO:
                character.setConPoder(3);
                token.setContParalisis(token.getContParalisis() + 3);
                break;
            case ROCA:
                if (token.getStone() == null) {
                    Elemento[] elementosTriada = new Elemento[3];

                    character.setConPoder(3);

                    elementosTriada[0] = token.getPersonajes()[0].getElemento();
                    elementosTriada[1] = token.getPersonajes()[1].getElemento();
                    elementosTriada[2] = token.getPersonajes()[2].getElemento();

                    token.setStone(Tablero.getTablero().getPiedra().clonar(60, 60, elementosTriada));
                } else {
                    activado = false;
                }
                break;
            case HIELO:
                character.setConPoder(3);
                token.setContCongelado(token.getContCongelado() + 1);
                break;
            default:
                break;
        }

        return activado;
    }

    private Elemento[] obtenerDesventajas(Personaje[] triada) {
        ArrayList<Elemento> desventajas = new ArrayList<Elemento>();
        desventajas.add(Elemento.FUEGO);
        desventajas.add(Elemento.AGUA);
        desventajas.add(Elemento.PLANTA);
        desventajas.add(Elemento.ELECTRICO);
        desventajas.add(Elemento.ROCA);
        desventajas.add(Elemento.HIELO);
        desventajas.remove(triada[0].getElemento());
        desventajas.remove(triada[1].getElemento());
        desventajas.remove(triada[2].getElemento());
        return (Elemento[]) desventajas.toArray();
    }

    public Elemento getElement() {
        return element;
    }

    public void setElement(Elemento element) {
        this.element = element;
    }

    public Personaje getCharacter() {
        return character;
    }

    public void setCharacter(Personaje character) {
        this.character = character;
    }

    public Ficha getToken() {
        return token;
    }

    public void setToken(Ficha token) {
        this.token = token;
    }

    public Tablero getBoard() {
        return board;
    }

    public void setBoard(Tablero board) {
        this.board = board;
    }

    public boolean isActivado() {
        return activado;
    }

    private void setActivado(boolean activado) {
        this.activado = activado;
    }

    public String getData() {
        return "Poder{" +
                "element=" + element +
                ", character=" + character +
                ", token=" + token +
                ", board=" + board +
                ", activado=" + activado +
                '}';
    }

    public boolean getEquation(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Poder poder = (Poder) o;
        return element == poder.element;
    }
}
