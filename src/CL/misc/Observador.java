package CL.misc;

import java.io.Serializable;

public interface Observador {
    void update(Serializable value);
}