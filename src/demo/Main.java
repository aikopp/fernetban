package demo;

import juego.pantallas.Inicio;
import juego.reglas.EscenaPartida;
import motor.Ventana;

public class Main {

    // Punto de entrada: aquí se conectan pantallas y reglas
    public static void main(String[] args) {
        Ventana.abrir("Fernetban", 1280, 720, new Inicio(() -> new EscenaPartida()));
    }
}
