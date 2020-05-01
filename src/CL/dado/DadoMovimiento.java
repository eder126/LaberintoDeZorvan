package CL.dado;

import CL.dado.Dado;

import java.util.Random;

/**
 * @author Ed
 * @version 2
 */
public class DadoMovimiento extends Dado {

    /**
     * Constructor base.
     */
    public DadoMovimiento () {
        super();
    }

    /**
     *
     * @param Min = Indica el valor minimo del dado de Movimiento.
     * @param Max = Indica el valor maximo del dado de Movimiento.
     */
    public DadoMovimiento (int Min, int Max) {
        super(Min, Max);
    }

    /**
     * Funcion principal de dados de juego.
     * @return valor int del resultado de girar el dado
     */
    @Override
    public int girar(){
        setValor(new Random().nextInt((getMaxValor() - getMinValor()) + 1) + getMinValor());
        return getValor();
    }

    /**
     * Funcion que cambia la imagen del dado correspondiente al valor actual.
     */
    @Override
    public String imagenDado() {
        switch (getValor()){
            case 1:
                setImagenDado("../img/dadosM/M1.png");
                break;
            case 2:
                setImagenDado("../img/dadosM/M2.png");
                break;
            case 3:
                setImagenDado("../img/dadosM/M3.png");
                break;
            case 4:
                setImagenDado("../img/dadosM/M4.png");
                break;
            case 5:
                setImagenDado("../img/dadosM/M5.png");
                break;
            case 6:
                setImagenDado("../img/dadosM/M6.png");
                break;
        }
        return getImagenDado();
    }
}
