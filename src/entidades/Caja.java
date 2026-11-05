package entidades;

import estructuras.Lista;
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import ui.Imagenes;

public class Caja extends Entidad implements Movible {

    // Imagen compartida por todas las cajas
    private static final BufferedImage SPRITE = Imagenes.ajustada("src/recursos/fernet.png", TAM_CASILLERO);

    // Constructor
    public Caja(int fila, int columna, int origenX, int origenY) {
        super(fila, columna, origenX, origenY);
    }

    // Movimiento
    @Override
    public void moverCelda(int deltaFila, int deltaColumna) {
        desplazar(deltaFila, deltaColumna);
    }

    // Una caja ocupa su casilla pero el jugador la puede empujar
    @Override
    public boolean bloqueaPaso() {
        return true;
    }

    @Override
    public Movible comoEmpujable() {
        return this;
    }

    // Está bien ubicada si hay una meta en su misma casilla
    @Override
    public boolean objetivoCumplido(Lista<Entidad> todas) {
        for (int i = 0; i < todas.tamanio(); i++) {
            Entidad otra = todas.obtener(i);
            if (otra.esMeta() && otra.estaEnCelda(getFila(), getColumna())) {
                return true;
            }
        }
        return false;
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
            g.setColor(new Color(170, 120, 60));
            g.fillRect(x + 6, y + 6, TAM_CASILLERO - 12, TAM_CASILLERO - 12);
        }
    }
}
