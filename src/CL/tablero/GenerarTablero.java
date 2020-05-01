package CL.tablero;

import CL.misc.Criatura;
import CL.misc.Ficha;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Random;

public class GenerarTablero {

    private static ArrayList<Integer> stonesPos = new ArrayList<Integer>();
    private static ArrayList<Integer> diablitosPos = new ArrayList<Integer>();
    private static ArrayList<Integer> querubinesPos = new ArrayList<Integer>();

    public static ArrayList getStonesPos(){
        return stonesPos;
    }

    public static ArrayList getDiablitosPos(){
        return diablitosPos;
    }

    public static ArrayList getQuerubinesPos(){
        return querubinesPos;
    }

    private static int generarNumero(ArrayList<Integer> arr, int min, int max){

        int num = new Random().nextInt((max - min) + 1) + min;
        if(stonesPos.contains(num))
            return generarNumero(arr, min, max);
        else if(diablitosPos.contains(num) || diablitosPos.contains(num+1) || diablitosPos.contains(num-2))
            return generarNumero(arr, min, max);
        else if(querubinesPos.contains(num)|| querubinesPos.contains(num+1) || querubinesPos.contains(num-1))
                return generarNumero(arr, min, max);
        else
            return num;
    }

    public static void generar(Ficha[] fichas) {
        //stonesPos.clear();
        //querubinesPos.clear();
        //diablitosPos.clear();
        Casilla[][] casillasT = new Casilla[10][10];
        //Max y min es de 100 a 1. Es decir (6) equivale a la pos 94
        final int CANTIDADSTONES = 5, CANTIDADDIABLITOS = 15, CANTIDADQUERUBINES = 10;
        for (short i = 0; i < CANTIDADSTONES; i++) {
            stonesPos.add(generarNumero(stonesPos, 2, 98));
            int pos[] = Tablero.getCoordenadas(stonesPos.get(i));
            casillasT[pos[0]][pos[1]] = new Casilla(stonesPos.get(i), Criatura.STONE);
        }
        for (short i = 0; i < CANTIDADDIABLITOS; i++) {
            diablitosPos.add(generarNumero(diablitosPos, 2, 89));
            int pos[] = Tablero.getCoordenadas(diablitosPos.get(i));
            casillasT[pos[0]][pos[1]] = new Casilla(diablitosPos.get(i), Criatura.DIABLITO);
        }
        for (short i = 0; i < CANTIDADQUERUBINES; i++) {
            querubinesPos.add(generarNumero(querubinesPos, 12, 98));
            int pos[] = Tablero.getCoordenadas(querubinesPos.get(i));
            casillasT[pos[0]][pos[1]] = new Casilla(querubinesPos.get(i), Criatura.QUERUBIN);
        }
        Collections.sort(stonesPos);
        Collections.sort(diablitosPos);
        Collections.sort(querubinesPos);

        //System.out.println(Arrays.asList(stonesPos).toString());


        for (int i = 1; i <= 100; i++) {
            if (!stonesPos.contains(i) && !diablitosPos.contains(i) && !querubinesPos.contains(i)) {
                int pos[] = Tablero.getCoordenadas(i);
                casillasT[pos[0]][pos[1]] = new Casilla(i, Criatura.VACIA);
            }
        }
        Tablero.getTablero(casillasT, fichas);
        //Tablero.getTablero().setCasillas(casillasT);
    }

    public static Casilla[][] reiniciar() {
        stonesPos.clear();
        querubinesPos.clear();
        diablitosPos.clear();
        Casilla[][] casillasT = new Casilla[10][10];
        //Max y min es de 100 a 1. Es decir (6) equivale a la pos 94
        final int CANTIDADSTONES = 5, CANTIDADDIABLITOS = 15, CANTIDADQUERUBINES = 10;
        for (short i = 0; i < CANTIDADSTONES; i++) {
            stonesPos.add(generarNumero(stonesPos, 2, 98));
            int pos[] = Tablero.getCoordenadas(stonesPos.get(i));
            casillasT[pos[0]][pos[1]] = new Casilla(stonesPos.get(i), Criatura.STONE);
        }
        for (short i = 0; i < CANTIDADDIABLITOS; i++) {
            diablitosPos.add(generarNumero(diablitosPos, 2, 89));
            int pos[] = Tablero.getCoordenadas(diablitosPos.get(i));
            casillasT[pos[0]][pos[1]] = new Casilla(diablitosPos.get(i), Criatura.DIABLITO);
        }
        for (short i = 0; i < CANTIDADQUERUBINES; i++) {
            querubinesPos.add(generarNumero(querubinesPos, 12, 98));
            int pos[] = Tablero.getCoordenadas(querubinesPos.get(i));
            casillasT[pos[0]][pos[1]] = new Casilla(querubinesPos.get(i), Criatura.QUERUBIN);
        }
        Collections.sort(stonesPos);
        Collections.sort(diablitosPos);
        Collections.sort(querubinesPos);

        for (int i = 1; i <= 100; i++) {
            if (!stonesPos.contains(i) && !diablitosPos.contains(i) && !querubinesPos.contains(i)) {
                int pos[] = Tablero.getCoordenadas(i);
                casillasT[pos[0]][pos[1]] = new Casilla(i, Criatura.VACIA);
            }
        }
        return casillasT;
    }
}
