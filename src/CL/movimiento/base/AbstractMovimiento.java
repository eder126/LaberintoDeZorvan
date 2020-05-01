package CL.movimiento.base;

public abstract class AbstractMovimiento implements IMovimiento {

    private int posActual, mover, total;

    public AbstractMovimiento(int posActual, int mover) {
        this.posActual = posActual;
        this.mover = mover;
        this.total = mover+posActual;
    }

    @Override
    public abstract int calcularMovimiento();


    public int getPosActual() {
        return posActual;
    }

    public void setPosActual(int posActual) {
        this.posActual = posActual;
    }

    public int getMover() {
        return mover;
    }

    public void setMover(int mover) {
        this.mover = mover;
    }

    public int getTotal() {
        return total;
    }

    public void setTotal(int total) {
        this.total = total;
    }
}


