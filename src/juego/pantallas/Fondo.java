package juego.pantallas;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import ui.Imagenes;

public class Fondo {

    // Atributos
    private final BufferedImage original;
    private final Color respaldo;
    private final int oscurecimiento;
    private BufferedImage preparada;

    // Constructor: oscurecimiento va de 0 (nada) a 255 (negro total)
    public Fondo(String ruta, Color respaldo, int oscurecimiento) {
        if (respaldo == null) {
            throw new IllegalArgumentException("el color de respaldo no puede ser nulo");
        }
        if (oscurecimiento < 0 || oscurecimiento > 255) {
            throw new IllegalArgumentException("el oscurecimiento va de 0 a 255: " + oscurecimiento);
        }
        this.respaldo = respaldo;
        this.oscurecimiento = oscurecimiento;
        this.original = Imagenes.cargar(ruta);
    }

    // Dibujo: si la imagen no existe se usa el color de respaldo
    public void dibujar(Graphics2D g, int ancho, int alto) {
        if (original == null) {
            g.setColor(respaldo);
            g.fillRect(0, 0, ancho, alto);
            return;
        }
        if (preparada == null || preparada.getWidth() != ancho || preparada.getHeight() != alto) {
            preparada = Imagenes.redimensionar(original, ancho, alto);
            Graphics2D p = preparada.createGraphics();
            p.setColor(new Color(0, 0, 0, oscurecimiento));
            p.fillRect(0, 0, ancho, alto);
            p.dispose();
        }
        g.drawImage(preparada, 0, 0, null);
    }
}
