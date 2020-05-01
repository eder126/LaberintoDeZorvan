package CL.misc;

public interface Sujeto {
    void addObserver(Observador o);
    void removeObserver(Observador o);
    void notifyObservers();
}