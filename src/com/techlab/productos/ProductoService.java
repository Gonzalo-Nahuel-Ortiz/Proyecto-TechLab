package com.techlab.productos;

import com.techlab.excepciones.ProductoNoEncontradoException;

import java.text.Normalizer;
import java.util.ArrayList;
import java.util.List;

/**
 * Encapsula la lógica de negocio sobre productos: agregar, listar, buscar, actualizar y eliminar.
 */
public class ProductoService {

    // Colección donde se guardan todos los productos (puede contener Producto, Bebida o Comida)
    private final List<Producto> productos = new ArrayList<>();

    public void agregarProducto(Producto producto) {
        productos.add(producto);
    }

    /** Devuelve una copia de la lista para que no se modifique desde afuera. */
    public List<Producto> listarProductos() {
        return new ArrayList<>(productos);
    }

    public boolean estaVacio() {
        return productos.isEmpty();
    }

    public Producto buscarPorId(int id) throws ProductoNoEncontradoException {
        for (Producto p : productos) {
            if (p.getId() == id) {
                return p;
            }
        }
        throw new ProductoNoEncontradoException("No existe un producto con ID " + id + ".");
    }

    /** Busca productos cuyo nombre contenga el texto ingresado (sin distinguir mayúsculas ni tildes). */
    public List<Producto> buscarPorNombre(String texto) {
        List<Producto> resultado = new ArrayList<>();
        String buscado = normalizar(texto);
        for (Producto p : productos) {
            if (normalizar(p.getNombre()).contains(buscado)) {
                resultado.add(p);
            }
        }
        return resultado;
    }

    /** Pasa a minúsculas y quita tildes, para que "cafe" encuentre "Café". */
    private String normalizar(String texto) {
        String sinTildes = Normalizer.normalize(texto.trim(), Normalizer.Form.NFD)
                .replaceAll("\\p{M}", "");
        return sinTildes.toLowerCase();
    }

    public void actualizarPrecio(int id, double nuevoPrecio) throws ProductoNoEncontradoException {
        buscarPorId(id).setPrecio(nuevoPrecio); // el setter valida que no sea negativo
    }

    public void actualizarStock(int id, int nuevoStock) throws ProductoNoEncontradoException {
        buscarPorId(id).setStock(nuevoStock); // el setter valida que no sea negativo
    }

    public Producto eliminarProducto(int id) throws ProductoNoEncontradoException {
        Producto p = buscarPorId(id);
        productos.remove(p);
        return p;
    }
}
