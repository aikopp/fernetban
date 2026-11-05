package juego.pantallas;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.event.KeyEvent;
import motor.Escena;
import ui.Texto;

public class Inicio extends Escena {

    // Atributos
    private final FabricaEscena partida;
    private final Fondo fondo = new Fondo("src/recursos/inicio.png", new Color(36, 24, 40), 140);

    // Constructor
    public Inicio(FabricaEscena partida) {
        if (partida == null) {
            throw new IllegalArgumentException("la fábrica de la partida no puede ser nula");
        }
        this.partida = partida;
    }

    // Actualización
    @Override
    public void actualizar(double dt) {
        if (getEntrada().fuePresionada(KeyEvent.VK_ENTER)) {
            getContexto().irA(partida.crear());
        }
        if (getEntrada().fuePresionada(KeyEvent.VK_R)) {
            getContexto().irA(new Reglas(partida));
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
        Texto.centrado(g, "Fernetban", ancho / 2.0, alto / 2.0 - 20, 48, Color.WHITE);
        Texto.centrado(g, "ENTER: jugar     R: reglas     ESC: salir", ancho / 2.0, alto / 2.0 + 70, 18,
                Color.LIGHT_GRAY);
    }
}
