package juego.pantallas;

import contratos.Dibujable;
import contratos.Estadisticas;
import java.awt.Color;
import java.awt.Graphics2D;
import ui.Texto;

public final class Hud implements Dibujable {

    // Constantes
    public static final int ALTO_HUD = 80;
    private static final int LADO = 18;
    private static final int SEPARACION = 8;
    private static final int MARGEN = 16;

    // Atributos
    private final Estadisticas estadisticas;
    private final int vidasMaximas;
    private final int nivelesTotales;
    private int ancho;

    // Constructores (sobrecarga: por defecto 3 vidas y 3 niveles)
    public Hud(Estadisticas estadisticas, int ancho) {
        this(estadisticas, ancho, 3, 3);
    }

    public Hud(Estadisticas estadisticas, int ancho, int vidasMaximas, int nivelesTotales) {
        if (estadisticas == null) {
            throw new IllegalArgumentException("las estadísticas no pueden ser nulas");
        }
        if (vidasMaximas <= 0 || nivelesTotales <= 0) {
            throw new IllegalArgumentException("vidas y niveles deben ser positivos");
        }
        this.estadisticas = estadisticas;
        this.vidasMaximas = vidasMaximas;
        this.nivelesTotales = nivelesTotales;
        setAncho(ancho);
    }

    // Acceso
    public int getAncho() {
        return ancho;
    }

    public void setAncho(int ancho) {
        if (ancho <= 0) {
            throw new IllegalArgumentException("el ancho debe ser positivo: " + ancho);
        }
        this.ancho = ancho;
    }

    // Dibujo
    @Override
    public void dibujar(Graphics2D g) {
        g.setColor(new Color(0, 0, 0, 190));
        g.fillRect(0, 0, ancho, ALTO_HUD);

        Texto.izquierda(g, "Puntaje: " + estadisticas.getPuntaje(), MARGEN, 32, 22, Color.WHITE);
        Texto.centrado(g, "Nivel " + estadisticas.getNivel() + "/" + nivelesTotales, ancho / 2.0, 32, 22,
                Color.WHITE);
        dibujarVidas(g);

        String mensaje = estadisticas.getMensaje();
        if (mensaje != null && !mensaje.isEmpty()) {
            Texto.centrado(g, mensaje, ancho / 2.0, 64, 16, Color.LIGHT_GRAY);
        }
    }

    // Vidas como cuadrados: llenos las que quedan, vacíos las perdidas
    private void dibujarVidas(Graphics2D g) {
        int restantes = Math.max(0, Math.min(vidasMaximas, estadisticas.getVidas()));
        int inicioX = ancho - MARGEN - vidasMaximas * (LADO + SEPARACION) + SEPARACION;
        Texto.derecha(g, "Vidas:", inicioX - 10, 32, 22, Color.WHITE);
        g.setColor(Color.WHITE);
        for (int i = 0; i < vidasMaximas; i++) {
            int x = inicioX + i * (LADO + SEPARACION);
            if (i < restantes) {
                g.fillRect(x, 15, LADO, LADO);
            } else {
                g.drawRect(x, 15, LADO - 1, LADO - 1);
            }
        }
    }
}
