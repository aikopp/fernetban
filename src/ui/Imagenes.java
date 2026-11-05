package ui;

import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;

public final class Imagenes {

    // Sin instancias
    private Imagenes() {
    }

    // Lee una imagen del disco; devuelve null si no existe o no se puede leer
    public static BufferedImage cargar(String ruta) {
        if (ruta == null || ruta.trim().isEmpty()) {
            throw new IllegalArgumentException("la ruta no puede estar vacía");
        }
        try {
            File archivo = new File(ruta);
            return archivo.exists() ? ImageIO.read(archivo) : null;
        } catch (IOException e) {
            return null;
        }
    }

    // Carga y escala a un tamaño exacto (texturas que llenan la casilla)
    public static BufferedImage escalada(String ruta, int ancho, int alto) {
        BufferedImage original = cargar(ruta);
        return original == null ? null : redimensionar(original, ancho, alto);
    }

    // Carga y escala para entrar en un cuadrado sin deformarse
    public static BufferedImage ajustada(String ruta, int lado) {
        BufferedImage original = cargar(ruta);
        if (original == null) {
            return null;
        }
        double factor = Math.min((double) lado / original.getWidth(), (double) lado / original.getHeight());
        int ancho = Math.max(1, (int) Math.round(original.getWidth() * factor));
        int alto = Math.max(1, (int) Math.round(original.getHeight() * factor));
        return redimensionar(original, ancho, alto);
    }

    // Reduce de a mitades para que no se vea dentado
    public static BufferedImage redimensionar(BufferedImage original, int ancho, int alto) {
        if (original == null || ancho <= 0 || alto <= 0) {
            throw new IllegalArgumentException("imagen o tamaño inválido");
        }
        BufferedImage actual = original;
        while (actual.getWidth() / 2 >= ancho && actual.getHeight() / 2 >= alto) {
            actual = escalarA(actual, actual.getWidth() / 2, actual.getHeight() / 2);
        }
        return escalarA(actual, ancho, alto);
    }

    private static BufferedImage escalarA(BufferedImage origen, int ancho, int alto) {
        BufferedImage copia = new BufferedImage(ancho, alto, BufferedImage.TYPE_INT_ARGB);
        Graphics2D g = copia.createGraphics();
        g.setRenderingHint(RenderingHints.KEY_INTERPOLATION, RenderingHints.VALUE_INTERPOLATION_BILINEAR);
        g.drawImage(origen, 0, 0, ancho, alto, null);
        g.dispose();
        return copia;
    }
}
