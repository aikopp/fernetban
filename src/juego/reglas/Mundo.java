package juego.reglas;

import contratos.Actualizable;
import contratos.Colisionable;
import contratos.Dibujable;
import entidades.Entidad;
import estructuras.Lista;
import estructuras.MiLista;
import java.awt.Graphics2D;
import java.util.Iterator;

public class Mundo implements Actualizable, Dibujable, Iterable<Entidad> {
    // Atributos
    private final int ancho;
    private final int alto;
    private final Lista<Entidad> entidades;
    private final Lista<Entidad> cuadro; // Arreglo reemplazado por TAD Lista
    private boolean sucio = true;

    // Constructor
    public Mundo(int ancho, int alto, Lista<Entidad> entidades) {
        if (ancho <= 0 || alto <= 0) {
            throw new IllegalArgumentException("dimensiones inválidas");
        }
        if (entidades == null) {
            throw new IllegalArgumentException("la lista no puede ser nula");
        }
        this.ancho = ancho;
        this.alto = alto;
        this.entidades = entidades;
        this.cuadro = new MiLista<>(); // Inicializamos la copia de trabajo
    }

    // Consultas
    public int getAncho() {
        return ancho;
    }

    public int getAlto() {
        return alto;
    }

    public int cantidad() {
        return entidades.tamanio();
    }

    @Override
    public Iterator<Entidad> iterator() {
        return entidades.iterator();
    }

    // Altas y bajas
    public void agregar(Entidad entidad) {
        if (entidad == null) {
            throw new IllegalArgumentException("la entidad no puede ser nula");
        }
        entidades.agregar(entidad);
        sucio = true;
    }

    public void vaciar() {
        refrescar();
        for (int i = 0; i < cuadro.tamanio(); i++) {
            cuadro.obtener(i).destruir();
        }
    }

    // Ciclo de juego
    @Override
    public void actualizar(double dt) {
        refrescar();
        for (int i = 0; i < cuadro.tamanio(); i++) {
            Entidad e = cuadro.obtener(i);
            if (e.estaViva()) {
                e.actualizar(dt);
                e.ajustarALimites(ancho, alto);
            }
        }
        resolverColisiones();
        for (int i = 0; i < cuadro.tamanio(); i++) {
            Entidad e = cuadro.obtener(i);
            if (e.estaViva()) {
                e.ajustarALimites(ancho, alto);
            }
        }
        limpiar();
    }

    @Override
    public void dibujar(Graphics2D g) {
        refrescar();
        for (int i = 0; i < cuadro.tamanio(); i++) {
            Entidad e = cuadro.obtener(i);
            if (e.estaViva()) {
                e.dibujar(g);
            }
        }
    }

    // Colisiones entre pares
    private void resolverColisiones() {
        for (int i = 0; i < cuadro.tamanio(); i++) {
            Entidad entidadI = cuadro.obtener(i);
            if (!(entidadI instanceof Colisionable)) {
                continue;
            }
            Colisionable a = (Colisionable) entidadI;
            for (int j = i + 1; j < cuadro.tamanio(); j++) {
                Entidad entidadJ = cuadro.obtener(j);
                if (!(entidadJ instanceof Colisionable)) {
                    continue;
                }
                if (!entidadI.estaViva() || !entidadJ.estaViva()) {
                    continue;
                }
                Colisionable b = (Colisionable) entidadJ;
                if (a.getLimites().intersects(b.getLimites())) {
                    a.alColisionar(b);
                    b.alColisionar(a);
                }
            }
        }
    }

    // Eliminación de entidades destruidas
    private void limpiar() {
        boolean hayMuertas = false;
        for (int i = 0; i < cuadro.tamanio(); i++) {
            if (!cuadro.obtener(i).estaViva()) {
                hayMuertas = true;
                break;
            }
        }
        if (!hayMuertas) {
            return;
        }
        for (int k = entidades.tamanio() - 1; k >= 0; k--) {
            if (!entidades.obtener(k).estaViva()) {
                entidades.eliminar(k);
            }
        }
        sucio = true;
    }

    // Copia de trabajo del cuadro usando Lista
    private void refrescar() {
        int n = entidades.tamanio();
        if (!sucio && n == cuadro.tamanio()) {
            return;
        }
        cuadro.vaciar();
        for (Entidad e : entidades) {
            cuadro.agregar(e);
        }
        sucio = false;
    }
}