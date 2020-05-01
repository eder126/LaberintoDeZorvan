package CL.tablero;

import CL.ataque.Elemento;
import CL.checkpoint.Memento;
import CL.dado.Dado;
import CL.dado.Fabrica_Dados;
import CL.misc.Ficha;
import CL.stone.Stone;
import CL.stone.StoneP;

import java.util.Arrays;

public class Tablero {

    private Casilla[][] casillas;
    private static Tablero tableroS = null;
    private Dado movementDice;
    private Stone piedra = new StoneP(100, 100, new Elemento[]{Elemento.FUEGO, Elemento.AGUA,
            Elemento.PLANTA});
    private Ficha[] fichas;
    private int turno;

    public Memento createMemento() {
        return new Memento(GenerarTablero.reiniciar(), this.getFichas());
    }

    public void setMemento(Memento m) {
        tableroS = new Tablero(m.getCasillas(), m.getFichas());
    }

    public static Tablero getTablero(Casilla[][] casillas, Ficha[] fichas) {
        if (tableroS == null) {
            tableroS = new Tablero(casillas, fichas);
        }
        return tableroS;
    }

    public static Tablero getTablero(Casilla[][] casillas) {
        if (tableroS == null) {
            tableroS = new Tablero(casillas);
        }
        return tableroS;
    }

    //Preguntar al resto sobre opinion
    public static Tablero getTablero() {
        if (tableroS == null) {
            tableroS = new Tablero();
        }
        return tableroS;
    }

    private Tablero() {
    }

    public static int[] getCoordenadas(int pos) {
        pos -= 10 * 10;
        int ROWS = 10;
        int COLS = 10;
        int row = pos / ROWS;
        int column = pos % COLS;

        if (row % 2 == 1) {
            column = COLS - 1 - column;
        }
        return new int[]{Math.abs(row), Math.abs(column)};
    }

    private Tablero(Casilla[][] casillas, Ficha[] fichas) {
        this.casillas = casillas;
        this.fichas = fichas;
        this.movementDice = new Fabrica_Dados().crearDado(true);
        this.turno = 0;
    }

    private Tablero(Casilla[][] casillas) {
        this.casillas = casillas;
    }

    public Casilla[][] getCasillas() {
        return casillas;
    }

    public void setCasillas(Casilla[][] casillas) {
        this.casillas = casillas;
    }

    public static Tablero getTableroS() {
        return tableroS;
    }

    public static void setTableroS(Tablero tableroS) {
        Tablero.tableroS = tableroS;
    }

    public Dado getMovementDice() {
        return movementDice;
    }

    public void setMovementDice(Dado movementDice) {
        this.movementDice = movementDice;
    }

    public Ficha[] getFichas() {
        return fichas;
    }

    public void setFichas(Ficha[] fichas) {
        this.fichas = fichas;
    }


    public Stone getPiedra() {
        return piedra;
    }

    public void setPiedra(Stone piedra) {
        this.piedra = piedra;
    }

    public int getTurno() {
        return turno;
    }

    public void setTurno(int turno) {
        this.turno = turno;
    }

    public int siguienteTurno() {
        if (turno == fichas.length - 1) {
            setTurno(0);
        } else {
            setTurno(getTurno() + 1);
        }
        return getTurno();
    }

    public boolean getEquation(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Tablero tablero = (Tablero) o;
        return Arrays.equals(casillas, tablero.casillas) &&
                movementDice.equals(tablero.movementDice) &&
                Arrays.equals(fichas, tablero.fichas);
    }


    public String getData() {
        return "Tablero{" +
                "casillas=" + Arrays.toString(casillas) +
                ", movementDice=" + movementDice +
                ", fichas=" + Arrays.toString(fichas) +
                '}';
    }
}
