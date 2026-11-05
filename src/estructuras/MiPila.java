package estructuras;

// PILA: funciona como una pila de platos
// El ultimo plato que pones arriba es el primero que sacas
// En Sokoban la usamos para DESHACER: cada movimiento se apila
// y al apretar Z se desapila el último que hiciste
//
// "implements Pila<T>" significa: esta clase cumple el contrato Pila
// o sea, tiene todos los metodos que la interfaz Pila exige.
// La <T> quiere decir "cualquier tipo": la pila puede guardar
// numeros, movimientos, cajas o lo que sea
public class MiPila<T> implements Pila<T> {

    // Atributos (lo que la pila guarda adentro)

    // "datos" es una fila de 16 casilleros donde guardamos los elementos
    // Es "private" para que nadie de afuera pueda tocarlo y romper la pila
    private Object[] datos = new Object[16];

    // Cuantos elementos hay guardados ahora
    // Tambien indica cual es el proximo casillero libre
    private int tamanio;

    // Operaciones

    // APILAR: poner un elemento arriba de todo
    @Override
    public void apilar(T elemento) {
        // Si los casilleros estan todos ocupados, conseguimos mas lugar
        if (tamanio == datos.length) {
            agrandar();
        }
        // Lo guardamos en el primer casillero libre
        datos[tamanio] = elemento;
        // y anotamos que ahora hay uno mas
        tamanio++;
    }

    // DESAPILAR: sacar el elemento de arriba de todo y devolverlo
    @Override
    @SuppressWarnings("unchecked")
    public T desapilar() {
        // No se puede sacar nada de una pila vacia: avisamos con un error
        if (estaVacia()) {
            throw new IllegalStateException("la pila está vacía");
        }
        // Hay uno menos, y "tamanio" pasa a apuntar al de arriba de todo
        tamanio--;
        // Lo guardamos para poder devolverlo
        T elemento = (T) datos[tamanio];
        // Vaciamos el casillero para no dejar basura
        datos[tamanio] = null;
        return elemento;
    }

    // TOPE: mirar cual esta arriba de todo, SIN sacarlo
    @Override
    @SuppressWarnings("unchecked")
    public T tope() {
        if (estaVacia()) {
            throw new IllegalStateException("la pila está vacía");
        }
        // El de arriba esta en la posicion tamanio - 1
        // (porque los casilleros se cuentan desde 0)
        return (T) datos[tamanio - 1];
    }

    // Consultas

    // Esta vacia? Si, si no hay ningun elemento en la pila
    @Override
    public boolean estaVacia() {
        return tamanio == 0;
    }

    // Cuantos elementos hay?
    @Override
    public int tamanio() {
        return tamanio;
    }

    // VACIAR: sacar todo de una vez
    @Override
    public void vaciar() {
        // Borramos cada casillero ocupado
        for (int i = 0; i < tamanio; i++) {
            datos[i] = null;
        }
        // y volvemos a contar desde cero
        tamanio = 0;
    }

    // Auxiliares (de uso interno: "private" porque solo la usa esta clase)

    // AGRANDAR: cuando se llenan los casilleros, creamos una fila
    // nueva del doble de tamaño y copiamos todo ahi
    private void agrandar() {
        Object[] nuevo = new Object[datos.length * 2];
        for (int i = 0; i < tamanio; i++) {
            nuevo[i] = datos[i];
        }
        // A partir de ahora usamos la fila nueva, mas grande
        datos = nuevo;
    }
}
