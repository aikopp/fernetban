# Informe final

## Cambios realizados

- Se conservaron el juego, sus gráficos, escenas, reglas y controles.
- `Mundo` permanece en `juego.reglas` y utiliza `MiLista` para su copia de trabajo, evitando un arreglo propio fuera de las estructuras.
- Se conservó `estructuras.Lista` con `extends Iterable<T>` porque `Mundo` usa `iterator()` y `for-each`, igual que el kit original.
- Se incorporó la prueba propia `juego.pruebas.PruebaPartida`.
- `ejecutar.sh` ejecuta esa prueba en modo headless cuando no hay entorno gráfico.
- Se completó la documentación de entrega.

## Requisitos cumplidos

El proyecto compila con Java 11 o superior, ofrece menú, partida y pantalla final con puntaje, tiene más de tres escenas conectadas, una jerarquía abstracta propia con subclases y polimorfismo, la interfaz propia `Movible`, encapsulamiento con validaciones y las implementaciones propias `MiLista`, `MiPila` y `MiCola`. Cada estructura cumple una función real y no se usan colecciones prohibidas de `java.util`.

## Pruebas ejecutadas

- `./compilar.sh` — OK.
- `./ejecutar.sh verificador.PruebaMisEstructuras` — OK: Lista 36/36, Pila 23/23, Cola 22/22.
- `./ejecutar.sh juego.pruebas.PruebaPartida` — OK: todas las pruebas pasaron.
- Compilación con `javac --release 11` — OK.

## Pendientes

`docs/integrantes.md` tiene campos para completar porque no se proporcionaron los nombres ni los paquetes. La prueba gráfica requiere una sesión con soporte para Swing; la lógica se verifica sin ventana mediante headless.
