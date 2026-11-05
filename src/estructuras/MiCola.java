package estructuras;

// COLA: funciona como la fila del super
// El primero que llega es el primero que se atiende
// En Sokoban la usamos para los NIVELES: se cargan en orden
// y cada vez que ganas uno, sale el siguiente
//
// "implements Cola<T>" significa: esta clase cumple el contrato Cola
// La <T> quiere decir "cualquier tipo" de elemento
public class MiCola<T> implements Cola<T> {

    // Atributos

    // Fila de 16 casilleros donde guardamos los elementos
    private Object[] datos = new Object[16];

    // En cual casillero esta el PRIMERO de la fila (el proximo en salir)
    // Cuando alguien sale, no corremos a todos: solo movemos este marcador.
    private int inicio;

    // Cuantos elementos hay en la fila ahora
    private int tamanio;

    // Operaciones

    // ENCOLAR: agregar un elemento al FINAL de la fila
    @Override
    public void encolar(T elemento) {
        // Si estan todo lleno, conseguimos mas lugar
        if (tamanio == datos.length) {
            agrandar();
        }
        // Calculamos el casillero que esta justo despues del ultimo
        // El % (resto de la division) hace que, si nos pasamos del final,
        // volvamos a empezar desde el casillero 0, como en una ronda
        // Por eso se llama "cola circular"
        int fin = (inicio + tamanio) % datos.length;
        datos[fin] = elemento;
        tamanio++;
    }

    // DESENCOLAR: sacar al PRIMERO de la fila y devolverlo
    @Override
    @SuppressWarnings("unchecked")
    public T desencolar() {
        // No se puede sacar a nadie de una fila vacia: avisamos con un error
        if (estaVacia()) {
            throw new IllegalStateException("la cola está vacía");
        }
        // Guardamos al primero para devolverlo
        T elemento = (T) datos[inicio];
        // Vaciamos su casillero.
        datos[inicio] = null;
        // El marcador avanza al siguiente (y da la vuelta si llega al final)
        inicio = (inicio + 1) % datos.length;
        tamanio--;
        return elemento;
    }

    // FRENTE: mirar cual es el primero, SIN sacarlo
    @Override
    @SuppressWarnings("unchecked")
    public T frente() {
        if (estaVacia()) {
            throw new IllegalStateException("la cola está vacía");
        }
        return (T) datos[inicio];
    }

    // Consultas

    // Esta vacia? Si, si no hay nadie en la fila
    @Override
    public boolean estaVacia() {
        return tamanio == 0;
    }

    // Cuantos hay en la fila?
    @Override
    public int tamanio() {
        return tamanio;
    }

    // VACIAR: sacar a todos de una vez
    @Override
    public void vaciar() {
        for (int i = 0; i < datos.length; i++) {
            datos[i] = null;
        }
        // El marcador vuelve al principio y no queda nadie
        inicio = 0;
        tamanio = 0;
    }

    // Auxiliares

    // AGRANDAR: crea una fila del doble de tamaño y copia a todos
    // EN ORDEN (empezando por el primero), asi en la fila nueva
    // el primero queda en el casillero 0
    private void agrandar() {
        Object[] nuevo = new Object[datos.length * 2];
        for (int i = 0; i < tamanio; i++) {
            nuevo[i] = datos[(inicio + i) % datos.length];
        }
        datos = nuevo;
        inicio = 0;
    }
}
