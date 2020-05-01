package CL.misc;

import CL.ataque.Elemento;
import CL.stone.Stone;
import javafx.scene.paint.Color;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;

public class Ficha implements Sujeto{

    private String nombre;
    private Color color;
    private int posicion, contCongelado, contParalisis, contDado;
    private boolean computadora;
    private ArrayList<Integer> stonesDerrotados;
    private Stone stone;
    private Personaje[] personajes;
    private List<Observador> observers = new ArrayList<Observador>();

    /**
     * Constructor base
     */
    public Ficha() {
        this.nombre = "John";
        this.color = Color.WHITE;
        this.setPosicion(1);
        this.setContCongelado(0);
        this.setContParalisis(0);
        this.setContDado(0);
        this.setStonesDerrotados(new ArrayList<>());
        Personaje[] arr = new Personaje[3];
        arr[0] = new Personaje(Elemento.ROCA);
        arr[1] = new Personaje(Elemento.FUEGO);
        arr[2] = new Personaje(Elemento.PLANTA);
        this.setPersonajes(arr);
        this.setComputadora(false);
    }

    /**
     * La posicion inicial siempre sera 0, al igual que los contadores de los efectos especiales.
     * @param nombre = Nombre de jugador.
     * @param color = Color seleccionado por el jugador.
     * @param stonesDerrotados = Direccion de los stones que ha derrotado el jugador.
     * @param personajes = Personajes (3) seleccionados por el jugador.
     */
    public Ficha(String nombre, Color color, int[] stonesDerrotados, Personaje[] personajes, boolean computadora) {
        this.nombre = nombre;
        this.color = color;
        this.setPosicion(1);
        this.setContCongelado(0);
        this.setContParalisis(0);
        this.setContDado(0);
        this.setStonesDerrotados(new ArrayList<>());
        this.setPersonajes(personajes);
        this.setComputadora(computadora);
    }

    /**
     *
     * @param nombre = Nombre de jugador.
     * @param personajes = Personajes (3) seleccionados por el jugador.
     * @param color = Color seleccionado por el jugador.
     */
    public Ficha(String nombre, Personaje[] personajes, Color color, boolean computadora) {
        this.nombre = nombre;
        this.color = color;
        this.setPosicion(1);
        this.setContCongelado(0);
        this.setContParalisis(0);
        this.setContDado(0);
        this.setStonesDerrotados(new ArrayList<>());
        this.setPersonajes(personajes);
        this.setComputadora(computadora);
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public Color getColor() {
        return color;
    }

    public void setColor(Color color) {
        this.color = color;
    }

    public int getPosicion() {
        return posicion;
    }

    public void setPosicion(int posicion) {
        this.posicion = posicion;
        if(posicion==100) notifyObservers();
    }

    public int getContCongelado() {
        return contCongelado;
    }

    public void setContCongelado(int contCongelado) {
        this.contCongelado = contCongelado;
    }

    public int getContParalisis() {
        return contParalisis;
    }

    public void setContParalisis(int contParalisis) {
        this.contParalisis = contParalisis;
    }

    public int getContDado() {
        return contDado;
    }

    public void setContDado(int contDado) {
        this.contDado = contDado;
    }

    public boolean getComputadora(){
        return this.computadora;
    }

    public void setComputadora(boolean computadora){
        this.computadora = computadora;
    }

    public ArrayList<Integer> getStonesDerrotados() {
        return stonesDerrotados;
    }

    public void setStonesDerrotados(ArrayList<Integer> stonesDerrotados) {
        this.stonesDerrotados = stonesDerrotados;
    }

    public Stone getStone() {
        return stone;
    }

    public void setStone(Stone stone) {
        this.stone = stone;
    }

    public Personaje[] getPersonajes() {
        return personajes;
    }

    public void setPersonajes(Personaje[] personajes) {
        this.personajes = personajes;
    }

    public void ReducirContadores() {
        if(getContCongelado() > 0)
            setContCongelado(getContCongelado() - 1);
        if(getContDado() > 0)
            setContDado(getContDado() - 1);
        if(getContParalisis() > 0)
            setContParalisis(getContParalisis() - 1);

        for(int i=0;i < getPersonajes().length; i++) {
            this.getPersonajes()[i].ReducirContadores();
        }
    }

    public boolean getEquation(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Ficha ficha = (Ficha) o;
        return posicion == ficha.posicion &&
                contCongelado == ficha.contCongelado &&
                contParalisis == ficha.contParalisis &&
                contDado == ficha.contDado &&
                nombre.equals(ficha.nombre) &&
                color.equals(ficha.color) &&
                stonesDerrotados.equals(ficha.stonesDerrotados) &&
                stone.equals(ficha.stone) &&
                Arrays.equals(personajes, ficha.personajes);
    }


    public String getData() {
        return "Ficha{" +
                "nombre='" + nombre + '\'' +
                ", color=" + color +
                ", posicion=" + posicion +
                ", contCongelado=" + contCongelado +
                ", contParalisis=" + contParalisis +
                ", contDado=" + contDado +
                ", stonesDerrotados=" + stonesDerrotados +
                ", stone=" + stone +
                ", personajes=" + Arrays.toString(personajes) +
                '}';
    }


    @Override
    public void addObserver(Observador o) {
        observers.add(o);
    }

    @Override
    public void removeObserver(Observador o) {
        observers.remove(o);
    }

    @Override
    public void notifyObservers() {
        for(Observador o : observers){
            o.update(this.nombre);
        }
    }
}
