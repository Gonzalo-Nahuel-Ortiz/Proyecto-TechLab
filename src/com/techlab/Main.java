package com.techlab;

import com.techlab.excepciones.ProductoNoEncontradoException;
import com.techlab.excepciones.StockInsuficienteException;
import com.techlab.pedidos.Pedido;
import com.techlab.pedidos.PedidoService;
import com.techlab.productos.Bebida;
import com.techlab.productos.Comida;
import com.techlab.productos.Producto;
import com.techlab.productos.ProductoService;

import java.util.List;
import java.util.Scanner;

/**
 * Clase principal: muestra el menú y orquesta las llamadas a los servicios.
 */
public class Main {

    private static final Scanner scanner = new Scanner(System.in);
    private static final ProductoService productoService = new ProductoService();
    private static final PedidoService pedidoService = new PedidoService();

    public static void main(String[] args) {
        cargarDatosDeEjemplo();

        int opcion;
        do {
            mostrarMenu();
            opcion = leerEntero("Elija una opción: ");
            System.out.println();

            switch (opcion) {
                case 1:
                    agregarProducto();
                    break;
                case 2:
                    listarProductos();
                    break;
                case 3:
                    buscarActualizarProducto();
                    break;
                case 4:
                    eliminarProducto();
                    break;
                case 5:
                    crearPedido();
                    break;
                case 6:
                    listarPedidos();
                    break;
                case 7:
                    System.out.println("¡Gracias por usar el sistema! Hasta luego.");
                    break;
                default:
                    System.out.println("Opción inválida. Ingrese un número del 1 al 7.");
            }
        } while (opcion != 7);

        scanner.close();
    }

    // ===================== MENÚ =====================

    private static void mostrarMenu() {
        System.out.println();
        System.out.println("=====================================");
        System.out.println("     SISTEMA DE GESTIÓN - TECHLAB");
        System.out.println("=====================================");
        System.out.println("1) Agregar producto");
        System.out.println("2) Listar productos");
        System.out.println("3) Buscar/Actualizar producto");
        System.out.println("4) Eliminar producto");
        System.out.println("5) Crear un pedido");
        System.out.println("6) Listar pedidos");
        System.out.println("7) Salir");
        System.out.println();
    }

    // ===================== OPCIONES =====================

    private static void agregarProducto() {
        System.out.println("--- AGREGAR PRODUCTO ---");
        System.out.println("Tipo: 1) General  2) Bebida  3) Comida");
        int tipo = leerEnteroEnRango("Seleccione el tipo: ", 1, 3);

        String nombre = leerTextoNoVacio("Nombre: ");
        double precio = leerDoubleNoNegativo("Precio: ");
        int stock = leerEnteroNoNegativo("Cantidad en stock: ");

        Producto producto;
        if (tipo == 2) {
            double volumen = leerDoublePositivo("Volumen en litros: ");
            producto = new Bebida(nombre, precio, stock, volumen);
        } else if (tipo == 3) {
            String vencimiento = leerTextoNoVacio("Fecha de vencimiento (dd/mm/aaaa): ");
            producto = new Comida(nombre, precio, stock, vencimiento);
        } else {
            producto = new Producto(nombre, precio, stock);
        }

        productoService.agregarProducto(producto);
        System.out.println("Producto agregado correctamente:");
        System.out.println(producto);
    }

    private static void listarProductos() {
        System.out.println("--- LISTADO DE PRODUCTOS ---");
        if (productoService.estaVacio()) {
            System.out.println("No hay productos registrados.");
            return;
        }
        // Polimorfismo: cada producto (General, Bebida o Comida) se muestra con su propio toString()
        for (Producto p : productoService.listarProductos()) {
            System.out.println(p);
        }
    }

    private static void buscarActualizarProducto() {
        System.out.println("--- BUSCAR / ACTUALIZAR PRODUCTO ---");
        if (productoService.estaVacio()) {
            System.out.println("No hay productos registrados.");
            return;
        }

        String criterio = leerTextoNoVacio("Ingrese el ID o el nombre del producto: ");
        Producto encontrado;

        try {
            // Si se ingresó un número, se busca por ID
            int id = Integer.parseInt(criterio);
            encontrado = productoService.buscarPorId(id);
        } catch (NumberFormatException e) {
            // Si no es un número, se busca por nombre
            List<Producto> resultados = productoService.buscarPorNombre(criterio);
            if (resultados.isEmpty()) {
                System.out.println("No se encontraron productos con ese nombre.");
                return;
            }
            if (resultados.size() == 1) {
                encontrado = resultados.get(0);
            } else {
                System.out.println("Se encontraron varios productos:");
                for (Producto p : resultados) {
                    System.out.println(p);
                }
                int id = leerEntero("Ingrese el ID del producto que quiere ver: ");
                try {
                    encontrado = productoService.buscarPorId(id);
                } catch (ProductoNoEncontradoException ex) {
                    System.out.println(ex.getMessage());
                    return;
                }
            }
        } catch (ProductoNoEncontradoException e) {
            System.out.println(e.getMessage());
            return;
        }

        System.out.println("Producto encontrado:");
        System.out.println(encontrado);

        System.out.println();
        System.out.println("¿Desea actualizar algún dato?");
        System.out.println("1) Precio  2) Stock  3) Ambos  0) No");
        int opcion = leerEnteroEnRango("Opción: ", 0, 3);

        try {
            if (opcion == 1 || opcion == 3) {
                double nuevoPrecio = leerDoubleNoNegativo("Nuevo precio: ");
                productoService.actualizarPrecio(encontrado.getId(), nuevoPrecio);
            }
            if (opcion == 2 || opcion == 3) {
                int nuevoStock = leerEnteroNoNegativo("Nuevo stock: ");
                productoService.actualizarStock(encontrado.getId(), nuevoStock);
            }
            if (opcion != 0) {
                System.out.println("Producto actualizado:");
                System.out.println(encontrado);
            }
        } catch (ProductoNoEncontradoException | IllegalArgumentException e) {
            System.out.println("Error: " + e.getMessage());
        }
    }

    private static void eliminarProducto() {
        System.out.println("--- ELIMINAR PRODUCTO ---");
        if (productoService.estaVacio()) {
            System.out.println("No hay productos registrados.");
            return;
        }
        listarProductos();
        int id = leerEntero("Ingrese el ID del producto a eliminar: ");

        try {
            Producto p = productoService.buscarPorId(id);
            System.out.println("Va a eliminar: " + p.getNombre());
            if (confirmar("¿Está seguro? (s/n): ")) {
                productoService.eliminarProducto(id);
                System.out.println("Producto eliminado.");
            } else {
                System.out.println("Operación cancelada.");
            }
        } catch (ProductoNoEncontradoException e) {
            System.out.println(e.getMessage());
        }
    }

    private static void crearPedido() {
        System.out.println("--- CREAR PEDIDO ---");
        if (productoService.estaVacio()) {
            System.out.println("No hay productos para pedir. Agregue productos primero.");
            return;
        }

        listarProductos();
        System.out.println();
        int cantidadProductos = leerEnteroPositivo("¿Cuántos productos distintos va a agregar? ");

        Pedido pedido = new Pedido();
        int agregados = 0;

        while (agregados < cantidadProductos) {
            System.out.println("Producto " + (agregados + 1) + " de " + cantidadProductos);
            int id = leerEntero("  ID del producto: ");
            try {
                Producto producto = productoService.buscarPorId(id);
                int cantidad = leerEnteroPositivo("  Cantidad: ");
                pedidoService.agregarProductoAPedido(pedido, producto, cantidad);
                System.out.println("  Agregado: " + producto.getNombre() + " x " + cantidad);
                agregados++;
            } catch (ProductoNoEncontradoException | StockInsuficienteException e) {
                System.out.println("  " + e.getMessage());
                if (!confirmar("  ¿Desea intentar con otro producto? (s/n): ")) {
                    break;
                }
            }
        }

        if (pedido.estaVacio()) {
            System.out.println("El pedido quedó vacío. No se creó ningún pedido.");
            return;
        }

        System.out.println();
        System.out.println("Resumen del pedido:");
        System.out.println(pedido);

        if (confirmar("¿Confirma el pedido? (s/n): ")) {
            try {
                pedidoService.confirmarPedido(pedido);
                System.out.println("Pedido #" + pedido.getId() + " confirmado. Se descontó el stock.");
            } catch (StockInsuficienteException e) {
                System.out.println("No se pudo confirmar: " + e.getMessage());
            }
        } else {
            System.out.println("Pedido cancelado. El stock no se modificó.");
        }
    }

    private static void listarPedidos() {
        System.out.println("--- LISTADO DE PEDIDOS ---");
        List<Pedido> pedidos = pedidoService.listarPedidos();
        if (pedidos.isEmpty()) {
            System.out.println("No hay pedidos realizados.");
            return;
        }
        for (Pedido p : pedidos) {
            System.out.println(p);
            System.out.println();
        }
    }

    // ===================== DATOS INICIALES =====================

    private static void cargarDatosDeEjemplo() {
        productoService.agregarProducto(new Producto("Café Premium", 4500.0, 20));
        productoService.agregarProducto(new Bebida("Agua Mineral", 1200.0, 50, 1.5));
        productoService.agregarProducto(new Comida("Medialunas x6", 3000.0, 15, "15/10/2026"));
    }

    // ===================== LECTURA DE DATOS (con manejo de excepciones) =====================

    private static int leerEntero(String mensaje) {
        while (true) {
            System.out.print(mensaje);
            String linea = scanner.nextLine().trim();
            try {
                return Integer.parseInt(linea);
            } catch (NumberFormatException e) {
                System.out.println("Debe ingresar un número entero válido.");
            }
        }
    }

    private static int leerEnteroEnRango(String mensaje, int min, int max) {
        while (true) {
            int valor = leerEntero(mensaje);
            if (valor >= min && valor <= max) {
                return valor;
            }
            System.out.println("Ingrese un valor entre " + min + " y " + max + ".");
        }
    }

    private static int leerEnteroNoNegativo(String mensaje) {
        while (true) {
            int valor = leerEntero(mensaje);
            if (valor >= 0) {
                return valor;
            }
            System.out.println("El valor no puede ser negativo.");
        }
    }

    private static int leerEnteroPositivo(String mensaje) {
        while (true) {
            int valor = leerEntero(mensaje);
            if (valor > 0) {
                return valor;
            }
            System.out.println("El valor debe ser mayor a 0.");
        }
    }

    private static double leerDouble(String mensaje) {
        while (true) {
            System.out.print(mensaje);
            // Se acepta coma o punto como separador decimal
            String linea = scanner.nextLine().trim().replace(',', '.');
            try {
                return Double.parseDouble(linea);
            } catch (NumberFormatException e) {
                System.out.println("Debe ingresar un número válido (ej: 1500.50).");
            }
        }
    }

    private static double leerDoubleNoNegativo(String mensaje) {
        while (true) {
            double valor = leerDouble(mensaje);
            if (valor >= 0) {
                return valor;
            }
            System.out.println("El valor no puede ser negativo.");
        }
    }

    private static double leerDoublePositivo(String mensaje) {
        while (true) {
            double valor = leerDouble(mensaje);
            if (valor > 0) {
                return valor;
            }
            System.out.println("El valor debe ser mayor a 0.");
        }
    }

    private static String leerTextoNoVacio(String mensaje) {
        while (true) {
            System.out.print(mensaje);
            String linea = scanner.nextLine().trim();
            if (!linea.isEmpty()) {
                return linea;
            }
            System.out.println("El texto no puede estar vacío.");
        }
    }

    private static boolean confirmar(String mensaje) {
        while (true) {
            System.out.print(mensaje);
            String r = scanner.nextLine().trim().toLowerCase();
            if (r.equals("s") || r.equals("si") || r.equals("sí")) {
                return true;
            }
            if (r.equals("n") || r.equals("no")) {
                return false;
            }
            System.out.println("Responda 's' o 'n'.");
        }
    }
}
