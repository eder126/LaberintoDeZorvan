package CL.dado;

/**
 * @author Ed
 * @version 1
 */
public class Fabrica_Dados implements Metodo_Fabrica_Dados {

    /**
     *
     * @param tipo = Indica si el Dado es de Movivmiento o de Ataque.
     * @return el tipo de Dado creado.
     */
    @Override
    public Dado crearDado(boolean tipo){
        if(tipo)
            return new DadoMovimiento();
        else
            return new DadoAtaque();
    }

    /**
     *
     * @param tipo = Indica si el Dado es de Movivmiento o de Ataque.
     * @param Min = Indica el valor minimo del dado.
     * @param Max = Indica el valor maximo del dado.
     * @return el tipo de Dado creado.
     */
    @Override
    public Dado crearDado(boolean tipo, int Min, int Max) {
        if(tipo)
            return new DadoMovimiento(Min, Max);
        else
            return new DadoAtaque(Min, Max);
    }
}
