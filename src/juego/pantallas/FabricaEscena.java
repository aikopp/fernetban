package juego.pantallas;

import motor.Escena;

// Permite crear una escena sin que este paquete conozca su clase,
// así pantallas no depende de reglas y no hay dependencias circulares
public interface FabricaEscena {

    Escena crear();
}
