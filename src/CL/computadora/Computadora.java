package CL.computadora;

import java.util.Random;

public class Computadora {
    public int elegir(int rango) {
        return new Random().nextInt(rango);
    }
}
