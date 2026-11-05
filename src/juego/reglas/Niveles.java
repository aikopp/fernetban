package juego.reglas;

import estructuras.Cola;
import estructuras.MiCola;

// Los tres niveles del juego, de menor a mayor dificultad
public final class Niveles {

    // Sin instancias
    private Niveles() {
    }

    // Los niveles salen de la cola en el mismo orden en que se encolan
    public static Cola<Nivel> crearCola() {
        Cola<Nivel> cola = new MiCola<>();
        cola.encolar(new Nivel(1,
                "#########\n"
                + "#J......#\n"
                + "#..C....#\n"
                + "#.......#\n"
                + "#......M#\n"
                + "#########"));
        cola.encolar(new Nivel(2,
                "###########\n"
                + "#..####...#\n"
                + "#.M..C..J.#\n"
                + "#...##.C..#\n"
                + "#....#..###\n"
                + "#M...#..###\n"
                + "###########"));
        cola.encolar(new Nivel(3,
                "############\n"
                + "#......#...#\n"
                + "#M.CM......#\n"
                + "#.#.#####..#\n"
                + "#J#..C..####\n"
                + "#.#........#\n"
                + "#.....#M.C.#\n"
                + "############"));
        return cola;
    }
}
