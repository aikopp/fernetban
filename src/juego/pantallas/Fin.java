package juego.pantallas;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.event.KeyEvent;
import motor.Escena;
import ui.Texto;

public class Fin extends Escena {

    // Atributos
    private final boolean victoria;
    private final int puntaje;
    private final FabricaEscena inicio;
    private final Fondo fondo = new Fondo("src/recursos/inicio.png", new Color(36, 24, 40), 150);

    // Constructor
    public Fin(boolean victoria, int puntaje, FabricaEscena inicio) {
        if (puntaje < 0) {
            throw new IllegalArgumentException("el puntaje no puede ser negativo: " + puntaje);
        }
        if (inicio == null) {
            throw new IllegalArgumentException("la fábrica del inicio no puede ser nula");
        }
        this.victoria = victoria;
        this.puntaje = puntaje;
        this.inicio = inicio;
    }

    // Consultas
    public boolean esVictoria() {
        return victoria;
    }

    public int getPuntaje() {
        return puntaje;
    }

    // Actualización
    @Override
    public void actualizar(double dt) {
        if (getEntrada().fuePresionada(KeyEvent.VK_ENTER)) {
            getContexto().irA(inicio.crear());
        }
        if (getEntrada().fuePresionada(KeyEvent.VK_ESCAPE)) {
            getContexto().salir();
        }
    }

    // Dibujo
    @Override
    public void dibujar(Graphics2D g) {
        int ancho = getContexto().getAncho();
        int alto = getContexto().getAlto();

        fondo.dibujar(g, ancho, alto);
        Texto.centrado(g, "Fin del juego", ancho / 2.0, alto / 2.0 - 50, 48, Color.WHITE);
        if (victoria) {
            Texto.centrado(g, "Felicidades, superaste el juego", ancho / 2.0, alto / 2.0, 22,
                    Color.LIGHT_GRAY);
        } else {
            Texto.centrado(g, "Te quedaste sin vidas", ancho / 2.0, alto / 2.0, 22, Color.LIGHT_GRAY);
        }
        Texto.centrado(g, "Puntaje: " + puntaje, ancho / 2.0, alto / 2.0 + 50, 30, Color.WHITE);
        Texto.centrado(g, "ENTER: volver al inicio     ESC: cerrar", ancho / 2.0, alto / 2.0 + 100, 18,
                Color.LIGHT_GRAY);
    }
}
