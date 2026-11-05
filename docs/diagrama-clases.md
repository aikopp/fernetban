# Diagrama de clases

```mermaid
classDiagram
    class Entidad {
      <<abstract>>
      -int fila
      -int columna
      +actualizar(dt)
      +dibujar(g)
      +bloqueaPaso()
    }
    Entidad <|-- Suelo
    Entidad <|-- Pared
    Entidad <|-- Meta
    Entidad <|-- Caja
    Entidad <|-- Jugador
    class Movible {
      <<interface>>
      +mover(deltaFila, deltaColumna)
    }
    Movible <|.. Caja
    Movible <|.. Jugador
    class MiLista
    class MiPila
    class MiCola
    MiLista ..|> Lista
    MiPila ..|> Pila
    MiCola ..|> Cola
    Tablero --> Mundo
    Mundo --> MiLista
    Tablero --> MiPila
    EscenaPartida --> MiCola
    EscenaPartida --> Tablero
    Inicio --> EscenaPartida
    Inicio --> Reglas
    EscenaPartida --> Fin
```

Las interfaces `Lista`, `Pila` y `Cola` pertenecen al kit; sus implementaciones son propias. `Lista` conserva `Iterable<T>` porque `juego.reglas.Mundo` usa `iterator()` y `for-each`, tal como en el kit original.
