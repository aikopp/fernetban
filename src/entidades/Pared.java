package entidades;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import ui.Imagenes;

public class Pared extends Entidad {

    // Textura compartida por todas las paredes
    private static final BufferedImage SPRITE = Imagenes.escalada("src/recursos/wall.jpg",
            TAM_CASILLERO, TAM_CASILLERO);

    // Constructor
    public Pared(int fila, int columna, int origenX, int origenY) {
        super(fila, columna, origenX, origenY);
    }

    // Una pared nunca se atraviesa ni se empuja
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
            g.drawImage(SPRITE, x, y, null);
        } else {
            g.setColor(new Color(150, 60, 40));
            g.fillRect(x, y, TAM_CASILLERO, TAM_CASILLERO);
        }
    }
}
