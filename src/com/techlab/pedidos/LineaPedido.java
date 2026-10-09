package com.techlab.pedidos;

import com.techlab.productos.Producto;

/**
 * Objeto intermedio que relaciona un producto con la cantidad pedida.
 * Guarda el precio al momento del pedido, así el total no cambia si después se actualiza el precio.
 */
public class LineaPedido {

    private final Producto producto;
    private int cantidad;
    private final double precioUnitario;

    public LineaPedido(Producto producto, int cantidad) {
        if (cantidad <= 0) {
            throw new IllegalArgumentException("La cantidad debe ser mayor a 0.");
        }
        this.producto = producto;
        this.cantidad = cantidad;
        this.precioUnitario = producto.getPrecio();
    }

    public Producto getProducto() {
        return producto;
    }

    public int getCantidad() {
        return cantidad;
    }

    /** Suma unidades si el mismo producto se agrega dos veces al pedido. */
    public void sumarCantidad(int extra) {
        this.cantidad += extra;
    }

    public double getPrecioUnitario() {
        return precioUnitario;
    }

    public double getSubtotal() {
        return precioUnitario * cantidad;
    }

    @Override
    public String toString() {
        return String.format("   - %-20s x %-4d  ($ %.2f c/u)  Subtotal: $ %.2f",
                producto.getNombre(), cantidad, precioUnitario, getSubtotal());
    }
}
