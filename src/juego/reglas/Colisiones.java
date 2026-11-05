package juego.reglas;

import estructuras.Lista;
import entidades.Entidad;

// Reglas de choque de Sokoban, sobre la grilla
public class Colisiones {

    // Primera entidad que ocupa la casilla y bloquea el paso, o null si está libre
    public Entidad bloqueadorEn(Lista<Entidad> entidades, int fila, int columna) {
        for (int i = 0; i < entidades.tamanio(); i++) {
            Entidad e = entidades.obtener(i);
            if (e.estaViva() && e.bloqueaPaso() && e.estaEnCelda(fila, columna)) {
                return e;
            }
        }
        return null;
    }

    // El jugador puede avanzar si la casilla está libre, o si hay una caja
    // y detrás de ella también hay lugar
    public boolean esMovimientoValido(Lista<Entidad> entidades, int filaJugador, int columnaJugador,
            int deltaFila, int deltaColumna, int filas, int columnas) {
        int fila = filaJugador + deltaFila;
        int columna = columnaJugador + deltaColumna;
        if (!estaDentro(fila, columna, filas, columnas)) {
            return false;
        }
        Entidad delante = bloqueadorEn(entidades, fila, columna);
        if (delante == null) {
            return true;
        }
        if (delante.comoEmpujable() == null) {
            return false;
        }
        int filaDetras = fila + deltaFila;
        int columnaDetras = columna + deltaColumna;
        if (!estaDentro(filaDetras, columnaDetras, filas, columnas)) {
            return false;
        }
        return bloqueadorEn(entidades, filaDetras, columnaDetras) == null;
    }

    private boolean estaDentro(int fila, int columna, int filas, int columnas) {
        return fila >= 0 && fila < filas && columna >= 0 && columna < columnas;
    }
}
