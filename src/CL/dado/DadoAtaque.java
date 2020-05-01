package CL.dado;

import CL.dado.Dado;

import java.util.Random;

/**
 * @author Ed
 * @version 2
 */
public class DadoAtaque extends Dado {

    /**
     * Constructor base.
     */
    public DadoAtaque () {
        super();
    }

    /**
     *
     * @param Min = Indica el valor minimo del dado de Ataque.
     * @param Max = Indica el valor maximo del dado de Ataque.
     */
    public DadoAtaque (int Min, int Max) {
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
                setImagenDado("../img/dadosA/A1.png");
                break;
            case 2:
                setImagenDado("../img/dadosA/A2.png");
                break;
            case 3:
                setImagenDado("../img/dadosA/A3.png");
                break;
            case 4:
                setImagenDado("../img/dadosA/A4.png");
                break;
            case 5:
                setImagenDado("../img/dadosA/A5.png");
                break;
            case 6:
                setImagenDado("../img/dadosA/A6.png");
                break;
        }
        return getImagenDado();
    }
}