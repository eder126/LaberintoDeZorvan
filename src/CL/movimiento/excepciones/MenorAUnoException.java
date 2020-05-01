package CL.movimiento.excepciones;

public class MenorAUnoException extends Exception {

    public MenorAUnoException() {
        super("La posición tiene que ser mayor a uno. Rango valido: (1-100)");
    }

}
