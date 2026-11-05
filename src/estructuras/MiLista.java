package estructuras;

// LISTA: una fila de elementos en orden, numerados desde 0
// A diferencia de la Pila y la Cola, aca se puede meter, mirar o sacar
// un elemento en CUALQUIER posicion.
// En Sokoban la usamos para guardar las CAJAS y CASILLAS del tablero
//
// "implements Lista<T>" significa: esta clase cumple el contrato Lista
// La <T> quiere decir "cualquier tipo" de elemento
public class MiLista<T> extends ListaBase<T> {

    // Atributos

    // Fila de 16 casilleros donde guardamos los elementos
    private Object[] datos = new Object[16];

    // Cuantos elementos hay guardados ahora
    private int tamanio;

    // Altas (agregar elementos)

    // AGREGAR: poner un elemento al FINAL de la lista.
    // Es lo mismo que insertar en la ultima posicion, asi que reusamos insertar
    @Override
    public void agregar(T elemento) {
        insertar(tamanio, elemento);
    }

    // INSERTAR: poner un elemento en una posicion elegida
    // Ejemplo: en [A, B, C], insertar(1, X) deja [A, X, B, C]
    @Override
    public void insertar(int indice, T elemento) {
        // La posicion tiene que existir (se permite el final, para agregar ahi)
        if (indice < 0 || indice > tamanio) {
            throw new IndexOutOfBoundsException("índice " + indice + ", tamaño " + tamanio);
        }
        // Si estan todo lleno, conseguimos mas lugar
        if (tamanio == datos.length) {
            agrandar();
        }
        // Corremos un lugar a la derecha a todos los que estan desde esa
        // posicion en adelante, empezando por el ultimo, para hacer espacio
        for (int i = tamanio; i > indice; i--) {
            datos[i] = datos[i - 1];
        }
        // Ponemos el nuevo en el hueco que quedo
        datos[indice] = elemento;
        tamanio++;
    }

    // Consultas

    // OBTENER: mirar el elemento de una posicion, sin sacarlo
    @Override
    @SuppressWarnings("unchecked")
    public T obtener(int indice) {
        validar(indice);
        return (T) datos[indice];
    }

    // INDICE DE: buscar en cual posicion esta un elemento
    // Si no lo encuentra, devuelve -1
    @Override
    public int indiceDe(T elemento) {
        // Revisamos uno por uno desde el principio
        for (int i = 0; i < tamanio; i++) {
            // Si el casillero esta vacia (null), coincide solo si buscamos null
            // Si no, usamos equals, que compara el contenido y no solo la direccion
            if (datos[i] == null ? elemento == null : datos[i].equals(elemento)) {
                return i;
            }
        }
        return -1;
    }

    // Cuantos elementos hay?
    @Override
    public int tamanio() {
        return tamanio;
    }

    // Esta vacia? Si, si no hay ningun elemento en la lista
    @Override
    public boolean estaVacia() {
        return tamanio == 0;
    }

    // Bajas (sacar elementos)

    // ELIMINAR: sacar el elemento de una posicion y devolverlo
    // Ejemplo: en [A, X, B, C], eliminar(1) devuelve X y deja [A, B, C]
    @Override
    @SuppressWarnings("unchecked")
    public T eliminar(int indice) {
        validar(indice);
        // Guardamos el que vamos a sacar, para devolverlo
        T elemento = (T) datos[indice];
        // Corremos un lugar a la izquierda a todos los que estaban despues,
        // para tapar el hueco
        for (int i = indice; i < tamanio - 1; i++) {
            datos[i] = datos[i + 1];
        }
        tamanio--;
        // El ultimo casillero quedo repetido: lo vaciamos
        datos[tamanio] = null;
        return elemento;
    }

    // VACIAR: sacar todo de una vez
    @Override
    public void vaciar() {
        for (int i = 0; i < tamanio; i++) {
            datos[i] = null;
        }
        tamanio = 0;
    }

    // Auxiliares

    // VALIDAR: revisa que la posicion exista. Si no, avisa con un error.
    // Ejemplo: en una lista de 3 elementos, las posiciones validas son 0, 1 y 2
    private void validar(int indice) {
        if (indice < 0 || indice >= tamanio) {
            throw new IndexOutOfBoundsException("índice " + indice + ", tamaño " + tamanio);
        }
    }

    // AGRANDAR: crea una fila del doble de tamaño y copia todo ahi 
    private void agrandar() {
        Object[] nuevo = new Object[datos.length * 2];
        for (int i = 0; i < tamanio; i++) {
            nuevo[i] = datos[i];
        }
        datos = nuevo;
    }
}
