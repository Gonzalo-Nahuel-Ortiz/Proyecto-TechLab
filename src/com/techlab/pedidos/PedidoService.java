package com.techlab.pedidos;

import com.techlab.excepciones.StockInsuficienteException;
import com.techlab.productos.Producto;

import java.util.ArrayList;
import java.util.List;

/**
 * Encapsula la lógica de pedidos: validar stock, confirmar (descontando stock) y listar.
 */
public class PedidoService {

    private final List<Pedido> pedidos = new ArrayList<>();

    /**
     * Agrega un producto a un pedido en armado, verificando que alcance el stock
     * (teniendo en cuenta lo que ya se cargó de ese mismo producto en el pedido).
     */
    public void agregarProductoAPedido(Pedido pedido, Producto producto, int cantidad)
            throws StockInsuficienteException {
        if (cantidad <= 0) {
            throw new IllegalArgumentException("La cantidad debe ser mayor a 0.");
        }
        int totalPedido = pedido.cantidadDe(producto) + cantidad;
        if (!producto.hayStock(totalPedido)) {
            throw new StockInsuficienteException(
                    "Stock insuficiente para '" + producto.getNombre() + "'. Disponible: "
                            + producto.getStock() + ", solicitado: " + totalPedido + ".");
        }
        pedido.agregarLinea(producto, cantidad);
    }

    /**
     * Confirma el pedido: vuelve a validar el stock, lo descuenta y guarda el pedido.
     */
    public void confirmarPedido(Pedido pedido) throws StockInsuficienteException {
        if (pedido.estaVacio()) {
            throw new IllegalArgumentException("El pedido no tiene productos.");
        }
        // Primero se valida todo, para no descontar a medias
        for (LineaPedido linea : pedido.getLineas()) {
            if (!linea.getProducto().hayStock(linea.getCantidad())) {
                throw new StockInsuficienteException(
                        "Stock insuficiente para '" + linea.getProducto().getNombre() + "'.");
            }
        }
        for (LineaPedido linea : pedido.getLineas()) {
            linea.getProducto().descontarStock(linea.getCantidad());
        }
        pedidos.add(pedido);
    }

    public List<Pedido> listarPedidos() {
        return new ArrayList<>(pedidos);
    }
}
