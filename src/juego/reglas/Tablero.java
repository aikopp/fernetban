package juego.reglas;

import contratos.Actualizable;
import contratos.Dibujable;
import estructuras.Lista;
import estructuras.Pila;
import java.awt.Graphics2D;
import entidades.Caja;
import entidades.Entidad;
import entidades.Jugador;
import entidades.Meta;
import entidades.Movible;
import entidades.Pared;
import entidades.Suelo;

public class Tablero implements Actualizable, Dibujable {

    // Atributos
    private final Lista<Entidad> entidades;
    private final Pila<RegistroMovimiento> historial;
    private final Colisiones colisiones = new Colisiones();
    private final int anchoVentana;
    private final int altoVentana;
    private final int margenSuperior;
    private Mundo mundo;
    private Nivel nivel;
    private Jugador jugador;
    private int origenX;
    private int origenY;

    // Constructor: la lista y la pila llegan armadas desde afuera
    public Tablero(Lista<Entidad> entidades, Pila<RegistroMovimiento> historial,
            int anchoVentana, int altoVentana, int margenSuperior) {
        if (entidades == null || historial == null) {
            throw new IllegalArgumentException("la lista y la pila no pueden ser nulas");
        }
        if (anchoVentana <= 0 || altoVentana <= 0 || margenSuperior < 0) {
            throw new IllegalArgumentException("dimensiones inválidas");
        }
        this.entidades = entidades;
        this.historial = historial;
        this.anchoVentana = anchoVentana;
        this.altoVentana = altoVentana;
        this.margenSuperior = margenSuperior;
    }

    // Carga un nivel desde cero (también sirve para reiniciarlo)
    public void cargar(Nivel nivel) {
        if (nivel == null) {
            throw new IllegalArgumentException("el nivel no puede ser nulo");
        }
        this.nivel = nivel;
        entidades.vaciar();
        historial.vaciar();
        mundo = new Mundo(anchoVentana, altoVentana, entidades);
        origenX = (anchoVentana - nivel.getColumnas() * Entidad.TAM_CASILLERO) / 2;
        origenY = margenSuperior
                + (altoVentana - margenSuperior - nivel.getFilas() * Entidad.TAM_CASILLERO) / 2;
        armarEntidades();
    }

    // Se arma por capas para que las cajas se dibujen sobre las metas
    private void armarEntidades() {
        for (int f = 0; f < nivel.getFilas(); f++) {
            for (int c = 0; c < nivel.getColumnas(); c++) {
                char simbolo = nivel.getCasilla(f, c);
                if (simbolo == '#') {
                    mundo.agregar(new Pared(f, c, origenX, origenY));
                } else if (simbolo != ' ') {
                    mundo.agregar(new Suelo(f, c, origenX, origenY));
                    if (simbolo == 'M') {
                        mundo.agregar(new Meta(f, c, origenX, origenY));
                    }
                }
            }
        }
        for (int f = 0; f < nivel.getFilas(); f++) {
            for (int c = 0; c < nivel.getColumnas(); c++) {
                if (nivel.getCasilla(f, c) == 'C') {
                    mundo.agregar(new Caja(f, c, origenX, origenY));
                }
            }
        }
        for (int f = 0; f < nivel.getFilas(); f++) {
            for (int c = 0; c < nivel.getColumnas(); c++) {
                if (nivel.getCasilla(f, c) == 'J') {
                    jugador = new Jugador(f, c, origenX, origenY);
                    mundo.agregar(jugador);
                }
            }
        }
    }

    // Intenta mover al jugador (y empujar una caja si hay); devuelve si se movió
    public boolean mover(int deltaFila, int deltaColumna) {
        if (jugador == null) {
            return false;
        }
        int fila = jugador.getFila();
        int columna = jugador.getColumna();
        if (!colisiones.esMovimientoValido(entidades, fila, columna, deltaFila, deltaColumna,
                nivel.getFilas(), nivel.getColumnas())) {
            return false;
        }
        Entidad delante = colisiones.bloqueadorEn(entidades, fila + deltaFila, columna + deltaColumna);
        Movible caja = delante == null ? null : delante.comoEmpujable();
        if (caja != null) {
            caja.moverCelda(deltaFila, deltaColumna);
        }
        jugador.moverCelda(deltaFila, deltaColumna);
        historial.apilar(new RegistroMovimiento(deltaFila, deltaColumna, caja));
        return true;
    }

    // Deshace el último movimiento; devuelve si había algo para deshacer
    public boolean deshacer() {
        if (historial.estaVacia()) {
            return false;
        }
        RegistroMovimiento ultimo = historial.desapilar();
        jugador.moverCelda(-ultimo.getDeltaFila(), -ultimo.getDeltaColumna());
        if (ultimo.empujoCaja()) {
            ultimo.getCajaEmpujada().moverCelda(-ultimo.getDeltaFila(), -ultimo.getDeltaColumna());
        }
        return true;
    }

    // Gana cuando todas las entidades cumplen su objetivo (cada caja sobre una meta)
    public boolean nivelCompleto() {
        for (int i = 0; i < entidades.tamanio(); i++) {
            if (!entidades.obtener(i).objetivoCumplido(entidades)) {
                return false;
            }
        }
        return true;
    }

    // Movimientos que lleva el nivel (los deshechos no cuentan)
    public int getMovimientos() {
        return historial.tamanio();
    }

    // Ciclo de juego
    @Override
    public void actualizar(double dt) {
        if (mundo != null) {
            mundo.actualizar(dt);
        }
    }

    @Override
    public void dibujar(Graphics2D g) {
        if (mundo != null) {
            mundo.dibujar(g);
        }
    }
}
