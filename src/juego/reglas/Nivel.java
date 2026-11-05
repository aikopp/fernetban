package juego.reglas;

import estructuras.Lista;
import estructuras.MiLista;

// Un nivel: su número y su diseño. Símbolos del diseño:
//   #  pared      .  suelo      M  meta      C  caja      J  jugador      (espacio)  fuera del mapa
public final class Nivel {

    // Atributos
    private final int numero;
    private final Lista<String> filas = new MiLista<>();
    private final int columnas;

    // Constructor: el diseño trae las filas separadas por saltos de línea
    public Nivel(int numero, String diseno) {
        if (numero <= 0) {
            throw new IllegalArgumentException("el número de nivel debe ser positivo: " + numero);
        }
        if (diseno == null || diseno.trim().isEmpty()) {
            throw new IllegalArgumentException("el diseño no puede estar vacío");
        }
        this.numero = numero;
        cortarFilas(diseno);
        this.columnas = calcularColumnas();
        validar();
    }

    // Consultas
    public int getNumero() {
        return numero;
    }

    public int getFilas() {
        return filas.tamanio();
    }

    public int getColumnas() {
        return columnas;
    }

    public char getCasilla(int fila, int columna) {
        if (fila < 0 || fila >= filas.tamanio()) {
            return ' ';
        }
        String texto = filas.obtener(fila);
        return columna >= 0 && columna < texto.length() ? texto.charAt(columna) : ' ';
    }

    // Separa el diseño en filas sin usar arreglos
    private void cortarFilas(String diseno) {
        int inicio = 0;
        while (inicio <= diseno.length()) {
            int fin = diseno.indexOf('\n', inicio);
            if (fin < 0) {
                fin = diseno.length();
            }
            String fila = diseno.substring(inicio, fin);
            if (!fila.isEmpty()) {
                filas.agregar(fila);
            }
            inicio = fin + 1;
        }
    }

    private int calcularColumnas() {
        int mayor = 0;
        for (int i = 0; i < filas.tamanio(); i++) {
            mayor = Math.max(mayor, filas.obtener(i).length());
        }
        return mayor;
    }

    // Rechaza diseños que no se pueden jugar
    private void validar() {
        int jugadores = 0;
        int cajas = 0;
        int metas = 0;
        for (int f = 0; f < getFilas(); f++) {
            for (int c = 0; c < columnas; c++) {
                char simbolo = getCasilla(f, c);
                switch (simbolo) {
                    case 'J':
                        jugadores++;
                        break;
                    case 'C':
                        cajas++;
                        break;
                    case 'M':
                        metas++;
                        break;
                    case '#':
                    case '.':
                    case ' ':
                        break;
                    default:
                        throw new IllegalArgumentException(
                                "símbolo inválido '" + simbolo + "' en el nivel " + numero);
                }
            }
        }
        if (jugadores != 1) {
            throw new IllegalArgumentException("el nivel " + numero + " necesita exactamente un jugador");
        }
        if (cajas == 0 || cajas != metas) {
            throw new IllegalArgumentException("el nivel " + numero + " necesita tantas metas como cajas");
        }
    }
}
