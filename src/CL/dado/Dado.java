package CL.dado;

/**
 * @author Ed
 * @version 2
 */
public abstract class Dado {
    protected int minValor;
    protected int maxValor;
    protected int valor;
    protected String imagenDado;

    /**
     * Constructor base.
     */
    public Dado() {
        setMinValor(1);
        setMaxValor(6);
        setValor(1);
        setImagenDado("@../img/M1.png");
    }

    /**
     *  Constructor que recibe los parametros del valor minimo y maximo del dado a crear.
     * @param Min = define el valor minimo del dado
     * @param Max = define el valor maximo del dado
     */
    public Dado(int Min, int Max) {
        setMinValor(Min);
        setMaxValor(Max);
        setValor(1);
        setImagenDado("@../img/M1.png");
    }

    public int getMinValor() {
        return minValor;
    }

    public void setMinValor(int mivValor) {
        this.minValor = mivValor;
    }

    public int getMaxValor() {
        return maxValor;
    }

    public void setMaxValor(int maxValor) {
        this.maxValor = maxValor;
    }

    public int getValor() {
        return valor;
    }

    public void setValor(int valor) {
        this.valor = valor;
    }

    public String getImagenDado() {
        return imagenDado;
    }

    public void setImagenDado(String imagenDado) {
        this.imagenDado = imagenDado;
    }

    /**
     * Funcion principal de dados de juego.
     * @return valor int del resultado de girar el dado
     */
    public abstract int girar();

    /**
     * Funcion que cambia la imagen del dado correspondiente al valor actual.
     */
    public abstract String imagenDado();
}
