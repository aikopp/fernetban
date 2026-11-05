package entidades;

import contratos.Actualizable;
import contratos.Dibujable;
import estructuras.Lista;

public abstract class Entidad implements Actualizable, Dibujable {

    // Constantes
    public static final int TAM_CASILLERO = 64;

    // Atributos
    private int fila;
    private int columna;
    private final int origenX;
    private final int origenY;
    private boolean viva = true;

    // Constructor: origenX y origenY son la esquina del tablero en pantalla
    protected Entidad(int fila, int columna, int origenX, int origenY) {
        this.origenX = origenX;
        this.origenY = origenY;
        setCelda(fila, columna);
    }

    // Posición en la grilla
    public int getFila() {
        return fila;
    }

    public int getColumna() {
        return columna;
    }

    protected final void setCelda(int fila, int columna) {
        if (fila < 0 || columna < 0) {
            throw new IllegalArgumentException("casilla inválida: " + fila + ", " + columna);
        }
        this.fila = fila;
        this.columna = columna;
    }

    protected final void desplazar(int deltaFila, int deltaColumna) {
        setCelda(fila + deltaFila, columna + deltaColumna);
    }

    public boolean estaEnCelda(int fila, int columna) {
        return this.fila == fila && this.columna == columna;
    }

    // Posición en pantalla (esquina superior izquierda de la casilla)
    public double getX() {
        return origenX + (double) columna * TAM_CASILLERO;
    }

    public double getY() {
        return origenY + (double) fila * TAM_CASILLERO;
    }

    // Ciclo de vida
    public boolean estaViva() {
        return viva;
    }

    public void destruir() {
        viva = false;
    }

    // Las entidades de Sokoban no se animan: se mueven solo por turnos
    @Override
    public void actualizar(double dt) {
    }

    // Gancho para los límites del mundo (el tablero ya los controla)
    public void ajustarALimites(double ancho, double alto) {
    }

    // Comportamiento que cada subclase define a su manera (polimorfismo)
    public boolean bloqueaPaso() {
        return false;
    }

    public boolean esMeta() {
        return false;
    }

    public Movible comoEmpujable() {
        return null;
    }

    public boolean objetivoCumplido(Lista<Entidad> todas) {
        return true;
    }
}
