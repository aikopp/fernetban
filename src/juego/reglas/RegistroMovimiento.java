package juego.reglas;

import entidades.Movible;

// Lo que hace falta para deshacer un movimiento
public class RegistroMovimiento {

    // Atributos
    private final int deltaFila;
    private final int deltaColumna;
    private final Movible cajaEmpujada; // null si el movimiento no empujó ninguna caja

    // Constructor
    public RegistroMovimiento(int deltaFila, int deltaColumna, Movible cajaEmpujada) {
        if (Math.abs(deltaFila) + Math.abs(deltaColumna) != 1) {
            throw new IllegalArgumentException("un movimiento es de una casilla: " + deltaFila + ", " + deltaColumna);
        }
        this.deltaFila = deltaFila;
        this.deltaColumna = deltaColumna;
        this.cajaEmpujada = cajaEmpujada;
    }

    // Consultas
    public int getDeltaFila() {
        return deltaFila;
    }

    public int getDeltaColumna() {
        return deltaColumna;
    }

    public Movible getCajaEmpujada() {
        return cajaEmpujada;
    }

    public boolean empujoCaja() {
        return cajaEmpujada != null;
    }
}
