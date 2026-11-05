package entidades;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import ui.Imagenes;

public class Meta extends Entidad {

    // Imagen compartida por todas las metas
    private static final BufferedImage SPRITE = Imagenes.ajustada("src/recursos/coca.png", TAM_CASILLERO);

    // Constructor
    public Meta(int fila, int columna, int origenX, int origenY) {
        super(fila, columna, origenX, origenY);
    }

    // Se puede pisar, pero las cajas se comparan contra ella
    @Override
    public boolean esMeta() {
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
            g.setColor(new Color(240, 215, 90));
            g.setStroke(new BasicStroke(3f));
            g.drawOval(x + 12, y + 12, TAM_CASILLERO - 24, TAM_CASILLERO - 24);
        }
    }
}
