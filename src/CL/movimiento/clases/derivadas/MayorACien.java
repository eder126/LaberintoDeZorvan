package CL.movimiento.clases.derivadas;

import CL.movimiento.base.AbstractMovimiento;

public class MayorACien extends AbstractMovimiento {

    public MayorACien(int posActual, int mover) {
        super(posActual, mover);
    }

    @Override
    public int calcularMovimiento() {
        return 100- Math.abs(this.getTotal()-100);
    }
}


