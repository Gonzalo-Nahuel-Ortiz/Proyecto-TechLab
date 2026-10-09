package com.techlab.pedidos;

import com.techlab.productos.Producto;

import java.util.ArrayList;
import java.util.List;

/**
 * Un pedido agrupa varias líneas (producto + cantidad) y sabe calcular su costo total.
 */
public class Pedido {

    private static int contadorId = 1;

    private final int id;
    private final List<LineaPedido> lineas = new ArrayList<>();

    public Pedido() {
        this.id = contadorId++;
    }

    public int getId() {
        return id;
    }

    public List<LineaPedido> getLineas() {
        return new ArrayList<>(lineas);
    }

    public boolean estaVacio() {
        return lineas.isEmpty();
    }

    /** Agrega un producto. Si ya estaba en el pedido, suma la cantidad a la línea existente. */
    public void agregarLinea(Producto producto, int cantidad) {
        for (LineaPedido linea : lineas) {
            if (linea.getProducto().getId() == producto.getId()) {
                linea.sumarCantidad(cantidad);
                return;
            }
        }
        lineas.add(new LineaPedido(producto, cantidad));
    }

    /** Devuelve cuántas unidades de un producto ya se cargaron en este pedido. */
    public int cantidadDe(Producto producto) {
        for (LineaPedido linea : lineas) {
            if (linea.getProducto().getId() == producto.getId()) {
                return linea.getCantidad();
            }
        }
        return 0;
    }

    /** Costo total = suma de (precio * cantidad) de cada línea. */
    public double calcularTotal() {
        double total = 0;
        for (LineaPedido linea : lineas) {
            total += linea.getSubtotal();
        }
        return total;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("Pedido #").append(id).append("\n");
        for (LineaPedido linea : lineas) {
            sb.append(linea).append("\n");
        }
        sb.append(String.format("   TOTAL: $ %.2f", calcularTotal()));
        return sb.toString();
    }
}
