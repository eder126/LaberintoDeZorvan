package CL.ataque;

import CL.stone.StoneP;
import CL.stone.Stone;
import CL.misc.Personaje;
import CL.tablero.Tablero;

import java.util.Objects;
import java.util.Random;

public class Ataque {
    private Personaje character;
    private Elemento elementoObjetivo;
    private Stone piedra;
    private int puntos;
    private boolean ejecutado;

    public Ataque() {
        Elemento[] elementos = new Elemento[3];

        elementos[0] = Elemento.values()[new Random().nextInt(Elemento.values().length)];
        do {
            elementos[1] = Elemento.values()[new Random().nextInt(Elemento.values().length)];
        } while (elementos[0] == elementos[1]);
        do {
            elementos[2] = Elemento.values()[new Random().nextInt(Elemento.values().length)];
        } while (elementos[0] == elementos[2] || elementos[1] == elementos[2]);

        character = new Personaje();
        elementoObjetivo = Elemento.values()[new Random().nextInt(Elemento.values().length)];
        piedra = Tablero.getTablero().getPiedra().clonar(100, 100, elementos);
    }

    public Ataque(Personaje character, Elemento elementoObjetivo, Stone piedra) {
        this.character = character;
        this.elementoObjetivo = elementoObjetivo;
        this.piedra = piedra;
    }

    public int calcularPuntos(Personaje character, Elemento element) {
        int pts;
        switch (character.getElemento()) {
            case FUEGO:
                pts = calcularPuntosSegunFuego(element);
                break;
            case AGUA:
                pts = calcularPuntosSegunAgua(element);
                break;
            case PLANTA:
                pts = calcularPuntosSegunPlanta(element);
                break;
            case ELECTRICO:
                pts = calcularPuntosSegunElectrico(element);
                break;
            case ROCA:
                pts = calcularPuntosSegunRoca(element);
                break;
            case HIELO:
                pts = calcularPuntosSegunHielo(element);
                break;
            default:
                pts = 10;
                break;
        }
        return pts;
    }

    public void ejecutarAtaque(boolean aplicarPtsExtra) {
        if (!ejecutado) {
            for (Elemento element : piedra.getElementos()) {
                if (element == elementoObjetivo) {
                    int pts = piedra.getVida() - calcularPuntos(character, elementoObjetivo);
                    if (aplicarPtsExtra) {
                        pts -= 5;
                    }
                    piedra.setVida(pts);
                    puntos = pts;
                    ejecutado = true;
                    break;
                }
            }
        }
    }

    private int calcularPuntosSegunFuego(Elemento element) {
        int pts = 10;
        switch (element) {
            case FUEGO:
                break;
            case AGUA:
                pts -= 5;
                break;
            case PLANTA:
                pts += 5;
                break;
            case ELECTRICO:
                pts -= 5;
                break;
            case ROCA:
                pts -= 5;
                break;
            case HIELO:
                pts += 5;
                break;
            default:
                break;
        }
        return pts;
    }

    private int calcularPuntosSegunAgua(Elemento element) {
        int pts = 10;
        switch (element) {
            case FUEGO:
                pts += 5;
                break;
            case AGUA:
                break;
            case PLANTA:
                pts -= 5;
                break;
            case ELECTRICO:
                pts -= 5;
                break;
            case ROCA:
                pts += 5;
                break;
            case HIELO:
                pts -= 5;
                break;
            default:
                break;
        }
        return pts;
    }

    private int calcularPuntosSegunPlanta(Elemento element) {
        int pts = 10;
        switch (element) {
            case FUEGO:
                pts -= 5;
                break;
            case AGUA:
                pts += 5;
                break;
            case PLANTA:
                break;
            case ELECTRICO:
                pts += 5;
                break;
            case ROCA:
                pts -= 5;
                break;
            case HIELO:
                pts -= 5;
                break;
            default:
                break;
        }
        return pts;
    }

    private int calcularPuntosSegunElectrico(Elemento element) {
        int pts = 10;
        switch (element) {
            case FUEGO:
                pts += 5;
                break;
            case AGUA:
                pts += 5;
                break;
            case PLANTA:
                pts -= 5;
                break;
            case ELECTRICO:
                break;
            case ROCA:
                pts -= 5;
                break;
            case HIELO:
                pts += 5;
                break;
            default:
                break;
        }
        return pts;
    }

    private int calcularPuntosSegunRoca(Elemento element) {
        int pts = 10;
        switch (element) {
            case FUEGO:
                pts += 5;
                break;
            case AGUA:
                pts -= 5;
                break;
            case PLANTA:
                pts += 5;
                break;
            case ELECTRICO:
                pts += 5;
                break;
            case ROCA:
                break;
            case HIELO:
                pts -= 5;
                break;
            default:
                break;
        }
        return pts;
    }

    private int calcularPuntosSegunHielo(Elemento element) {
        int pts = 10;
        switch (element) {
            case FUEGO:
                pts -= 5;
                break;
            case AGUA:
                pts += 5;
                break;
            case PLANTA:
                pts += 5;
                break;
            case ELECTRICO:
                pts -= 5;
                break;
            case ROCA:
                pts += 5;
                break;
            case HIELO:
                break;
            default:
                break;
        }
        return pts;
    }

    public Personaje getCharacter() {
        return character;
    }

    public void setCharacter(Personaje character) {
        this.character = character;
    }

    public Elemento getElementoObjetivo() {
        return elementoObjetivo;
    }

    public void setElementoObjetivo(Elemento elementoObjetivo) {
        this.elementoObjetivo = elementoObjetivo;
    }

    public Stone getPiedra() {
        return piedra;
    }

    public void setPiedra(Stone piedra) {
        this.piedra = piedra;
    }

    public int getPuntos() {
        return puntos;
    }

    private void setPuntos(int puntos) {
        this.puntos = puntos;
    }

    public boolean isEjecutado() {
        return ejecutado;
    }

    private void setEjecutado(boolean ejecutado) {
        this.ejecutado = ejecutado;
    }

    public String getData() {
        return "Ataque{" +
                "character=" + character +
                ", elementoObjetivo=" + elementoObjetivo +
                ", piedra=" + piedra +
                ", puntos=" + puntos +
                ", ejecutado=" + ejecutado +
                '}';
    }

    public boolean getEquation(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Ataque ataque = (Ataque) o;
        return puntos == ataque.puntos &&
                ejecutado == ataque.ejecutado &&
                Objects.equals(character, ataque.character) &&
                elementoObjetivo == ataque.elementoObjetivo &&
                Objects.equals(piedra, ataque.piedra);
    }
}
