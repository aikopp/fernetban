package juego.reglas;

import contratos.Estadisticas;
import estructuras.Cola;
import estructuras.MiLista;
import estructuras.MiPila;
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.event.KeyEvent;
import juego.pantallas.Fin;
import juego.pantallas.Fondo;
import juego.pantallas.Hud;
import juego.pantallas.Inicio;
import juego.pantallas.FabricaEscena;
import motor.Entrada;
import motor.Escena;

public class EscenaPartida extends Escena implements Estadisticas {

    // Constantes de las reglas
    private static final int VIDAS_INICIALES = 3;
    private static final int PUNTOS_POR_NIVEL = 1000;
    private static final int COSTO_MOVIMIENTO = 10;

    // Fábricas para volver al inicio sin que pantallas dependa de reglas
    private static final FabricaEscena NUEVA_PARTIDA = () -> new EscenaPartida();
    private static final FabricaEscena INICIO = () -> new Inicio(NUEVA_PARTIDA);

    // Atributos
    private Cola<Nivel> niveles;
    private Tablero tablero;
    private Hud hud;
    private Fondo fondo;
    private Nivel nivelEnCurso;
    private int vidas;
    private int puntajeAcumulado;

    // Entrada a la escena
    @Override
    public void alEntrar() {
        niveles = Niveles.crearCola();
        fondo = new Fondo("src/recursos/fondo.jpg", new Color(30, 22, 18), 150);
        hud = new Hud(this, getContexto().getAncho(), VIDAS_INICIALES, niveles.tamanio());
        tablero = new Tablero(new MiLista<>(), new MiPila<>(),
                getContexto().getAncho(), getContexto().getAlto(), Hud.ALTO_HUD);
        vidas = VIDAS_INICIALES;
        puntajeAcumulado = 0;
        avanzarNivel();
    }

    // Actualización
    @Override
    public void actualizar(double dt) {
        Entrada teclado = getEntrada();

        if (teclado.fuePresionada(KeyEvent.VK_ESCAPE)) {
            getContexto().irA(INICIO.crear());
            return;
        }
        if (teclado.fuePresionada(KeyEvent.VK_R)) {
            perderVida();
            return;
        }
        if (teclado.fuePresionada(KeyEvent.VK_Z)) {
            tablero.deshacer();
        }

        int deltaFila = 0;
        int deltaColumna = 0;
        if (teclado.fuePresionada(KeyEvent.VK_LEFT)) {
            deltaColumna = -1;
        } else if (teclado.fuePresionada(KeyEvent.VK_RIGHT)) {
            deltaColumna = 1;
        } else if (teclado.fuePresionada(KeyEvent.VK_UP)) {
            deltaFila = -1;
        } else if (teclado.fuePresionada(KeyEvent.VK_DOWN)) {
            deltaFila = 1;
        }
        if ((deltaFila != 0 || deltaColumna != 0) && tablero.mover(deltaFila, deltaColumna)) {
            resolverTurno();
        }
        tablero.actualizar(dt);
    }

    // Después de cada movimiento: ¿ganó el nivel? ¿se quedó sin puntos?
    private void resolverTurno() {
        if (tablero.nivelCompleto()) {
            puntajeAcumulado += puntajeDelNivel();
            avanzarNivel();
        } else if (puntajeDelNivel() == 0) {
            perderVida();
        }
    }

    // Pasa al siguiente nivel de la cola, o termina si ya no quedan
    private void avanzarNivel() {
        if (niveles.estaVacia()) {
            getContexto().irA(new Fin(true, puntajeAcumulado, INICIO));
            return;
        }
        nivelEnCurso = niveles.desencolar();
        tablero.cargar(nivelEnCurso);
    }

    // Cuesta una vida y reinicia el nivel; sin vidas termina la partida
    private void perderVida() {
        vidas--;
        if (vidas <= 0) {
            getContexto().irA(new Fin(false, puntajeAcumulado, INICIO));
            return;
        }
        tablero.cargar(nivelEnCurso);
    }

    private int puntajeDelNivel() {
        return Math.max(0, PUNTOS_POR_NIVEL - COSTO_MOVIMIENTO * tablero.getMovimientos());
    }

    // Dibujo
    @Override
    public void dibujar(Graphics2D g) {
        fondo.dibujar(g, getContexto().getAncho(), getContexto().getAlto());
        tablero.dibujar(g);
        hud.dibujar(g);
    }

    // Datos para el HUD
    @Override
    public int getPuntaje() {
        return puntajeAcumulado + puntajeDelNivel();
    }

    @Override
    public int getVidas() {
        return Math.max(0, vidas);
    }

    @Override
    public int getNivel() {
        return nivelEnCurso.getNumero();
    }

    @Override
    public String getMensaje() {
        return "Movimientos: " + tablero.getMovimientos()
                + "     Z: deshacer     R: reiniciar (-1 vida)     ESC: menú";
    }
}
