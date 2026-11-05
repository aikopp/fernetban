package entidades;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import ui.Imagenes;

public class Suelo extends Entidad {

    // Textura compartida por todas las casillas de suelo
    private static final BufferedImage SPRITE = Imagenes.escalada("src/recursos/floor1.jpg",
            TAM_CASILLERO, TAM_CASILLERO);

    // Constructor
    public Suelo(int fila, int columna, int origenX, int origenY) {
        super(fila, columna, origenX, origenY);
    }

    // Dibujo
    @Override
    public void dibujar(Graphics2D g) {
        int x = (int) getX();
        int y = (int) getY();
        if (SPRITE != null) {
            g.drawImage(SPRITE, x, y, null);
        } else {
            g.setColor(new Color(110, 80, 50));
            g.fillRect(x, y, TAM_CASILLERO, TAM_CASILLERO);
        }
    }
}
