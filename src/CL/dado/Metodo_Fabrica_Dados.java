package CL.dado;

/**
 * @author Ed
 * @version 1
 */
public interface Metodo_Fabrica_Dados {
    /**
     *
     * @param tipo = Indica si el Dado es de Movivmiento o de Ataque
     * @return el tipo de Dado creado.
     */
    public Dado crearDado(boolean tipo);

    /**
     *
     * @param tipo = Indica si el Dado es de Movivmiento o de Ataque.
     * @param Min = Indica el valor minimo del dado.
     * @param Max = Indica el valor maximo del dado.
     * @return el tipo de Dado creado.
     */
    public Dado crearDado(boolean tipo, int Min, int Max);
}
