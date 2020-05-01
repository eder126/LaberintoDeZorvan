package CL.movimiento.clases.derivadas;

import CL.movimiento.base.AbstractMovimiento;

public class MenorACien extends AbstractMovimiento {

    public MenorACien(int posActual, int mover) {
        super(posActual, mover);
    }

    @Override
    public int calcularMovimiento() {
        return this.getTotal();
    }
}


