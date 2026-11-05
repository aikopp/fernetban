#!/bin/sh
set -eu
CLASE="${1:-demo.Main}"
if [ "$CLASE" = "juego.pruebas.PruebaPartida" ]; then
    exec java -Djava.awt.headless=true -cp out "$CLASE"
fi
exec java -cp out "$CLASE"
