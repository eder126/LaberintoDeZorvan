package CL.movimiento.excepciones;

public class ConstructorIncorrectoException extends Exception {

    public ConstructorIncorrectoException() {
        super("El constructor utilizado no es correcto para movimiento.");
    }

}
