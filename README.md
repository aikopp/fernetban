# Discometro

Juego 2D en Java basado en Sokoban con deshacer. Requiere JDK 11 o superior.

## Compilar y ejecutar

Desde la carpeta raíz:

```bash
./compilar.sh
./ejecutar.sh
```

Verificaciones:

```bash
./ejecutar.sh verificador.PruebaMisEstructuras
./ejecutar.sh juego.pruebas.PruebaPartida
```

La prueba de partida no abre una ventana. Para jugar visualmente se necesita un entorno con soporte para Java Swing.

## Cómo jugar

- `ENTER`: comenzar desde el menú.
- `R`: ver las reglas desde el menú o reiniciar el nivel.
- Flechas: mover al jugador.
- `Z`: deshacer el último movimiento.
- `ESC`: volver al menú o salir.

La partida tiene tres niveles, tres vidas y puntaje. Al completar todos los niveles se muestra la pantalla final con el puntaje.

## Paquetes

- `motor`, `contratos`, `ui` y `verificador`: kit y verificador.
- `entidades`: entidad abstracta, jugador, cajas, paredes, suelo y metas.
- `estructuras`: `MiLista`, `MiPila` y `MiCola`.
- `juego.reglas`: niveles, tablero, mundo, colisiones y reglas.
- `juego.pantallas`: menú, reglas, HUD y pantalla final.
- `juego.pruebas`: prueba propia de la partida.

## Uso de las estructuras

- `MiLista`: entidades y filas del tablero.
- `MiPila`: movimientos para deshacer con `Z`.
- `MiCola`: niveles en orden.

`Lista` conserva `Iterable<T>` porque `juego.reglas.Mundo` usa `iterator()` y `for-each`, como el kit original. Los arreglos de almacenamiento se usan dentro de las estructuras propias y en el kit.

## Documentación

- `docs/diagrama-clases.md`
- `docs/integrantes.md`
- `docs/informe-final.md`
