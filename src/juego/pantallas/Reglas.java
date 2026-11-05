package juego.pantallas;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.event.KeyEvent;
import motor.Escena;
import ui.Texto;

public class Reglas extends Escena {

    // Atributos
    private final FabricaEscena partida;
    private final Fondo fondo = new Fondo("src/recursos/inicio.png", new Color(24, 30, 40), 190);
    private int y;

    // Constructor
    public Reglas(FabricaEscena partida) {
        if (partida == null) {
            throw new IllegalArgumentException("la fábrica de la partida no puede ser nula");
        }
        this.partida = partida;
    }

    // Actualización
    @Override
    public void actualizar(double dt) {
        if (getEntrada().fuePresionada(KeyEvent.VK_ENTER) || getEntrada().fuePresionada(KeyEvent.VK_ESCAPE)) {
            getContexto().irA(new Inicio(partida));
        }
    }

    // Dibujo
    @Override
    public void dibujar(Graphics2D g) {
        int ancho = getContexto().getAncho();
        int alto = getContexto().getAlto();

        fondo.dibujar(g, ancho, alto);
        Texto.centrado(g, "Reglas", ancho / 2.0, 110, 44, Color.WHITE);

        // Para agregar una regla, copia una línea "regla(...)" y cambia el texto
        y = 190;
        regla(g, "1. Empuja todas las botellas de fernet hasta los vasos de coca.");
        regla(g, "2. Movete con las flechas. Solo podes empujar una caja a la vez.");
        regla(g, "3. Z deshace el último movimiento, tantas veces como quieras.");
        regla(g, "4. Cada nivel vale 1000 puntos y perdes 10 por cada movimiento.");
        regla(g, "5. Si llegas a 0 puntos en un nivel, o reinicias con R, perdes una vida.");
        regla(g, "6. Tenes 3 vidas y hay 3 niveles. Sin vidas, se acaba el juego.");

        Texto.centrado(g, "ENTER o ESC: volver", ancho / 2.0, alto - 40, 18, Color.LIGHT_GRAY);
    }

    // Dibuja una regla y baja a la siguiente línea
    private void regla(Graphics2D g, String texto) {
        Texto.izquierda(g, texto, 140, y, 22, Color.WHITE);
        y += 46;
    }
}
