package entidades;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import ui.Imagenes;

public class Jugador extends Entidad implements Movible {

    // Imagen del personaje
    private static final BufferedImage SPRITE = Imagenes.ajustada("src/recursos/jugador.png", TAM_CASILLERO);

    // Constructor
    public Jugador(int fila, int columna, int origenX, int origenY) {
        super(fila, columna, origenX, origenY);
    }

    // Movimiento
    @Override
    public void moverCelda(int deltaFila, int deltaColumna) {
        desplazar(deltaFila, deltaColumna);
    }

    @Override
    public boolean bloqueaPaso() {
        return true;
    }

    // Dibujo
    @Override
    public void dibujar(Graphics2D g) {
        int x = (int) getX();
        int y = (int) getY();
        if (SPRITE != null) {
            g.drawImage(SPRITE, x + (TAM_CASILLERO - SPRITE.getWidth()) / 2,
                    y + (TAM_CASILLERO - SPRITE.getHeight()) / 2, null);
        } else {
            g.setColor(Color.WHITE);
            g.fillOval(x + 14, y + 14, TAM_CASILLERO - 28, TAM_CASILLERO - 28);
        }
    }
}
