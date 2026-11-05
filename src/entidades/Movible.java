package entidades;

// Contrato de lo que se mueve casilla por casilla (jugador y cajas)
public interface Movible {

    int getFila();

    int getColumna();

    void moverCelda(int deltaFila, int deltaColumna);
}
