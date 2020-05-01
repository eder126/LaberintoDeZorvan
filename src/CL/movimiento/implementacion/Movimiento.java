package CL.movimiento.implementacion;

import CL.movimiento.base.AbstractMovimiento;
import CL.movimiento.clases.derivadas.*;
import CL.movimiento.excepciones.ConstructorIncorrectoException;
import CL.movimiento.excepciones.MenorAUnoException;

public class Movimiento {

    private AbstractMovimiento _estrategia;
    private int total = 0, posActual = 0, mover = 0;
    public Movimiento(int posActual, int mover) throws
            MenorAUnoException, ConstructorIncorrectoException {
        if(posActual+posActual < 1)
            throw new MenorAUnoException();
        else if(posActual==0 || mover==0)
            throw new ConstructorIncorrectoException();
        this.posActual = posActual;
        this.mover = mover;
        this.total = this.posActual + this.mover;
    }


    public void estableceFormula() throws ConstructorIncorrectoException {
        if (esMayorACien()) {
            _estrategia = new MayorACien(posActual, mover);
        } else if (esMenorACien()) {
            _estrategia = new MenorACien(posActual, mover);
        } else {
            throw new ConstructorIncorrectoException();
        }
    }

    public int getNuevaPos() {
        return _estrategia.calcularMovimiento();
    }

    private boolean esMayorACien() {
        return total>100;
    }

    private boolean esMenorACien() {
        return total>=1;
    }

}

