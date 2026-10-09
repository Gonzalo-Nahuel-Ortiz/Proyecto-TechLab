package com.techlab.productos;

/**
 * Representa un producto genérico del sistema.
 * Los atributos son privados (encapsulamiento) y se acceden mediante getters/setters.
 */
public class Producto {

    // Contador estático para autogenerar IDs únicos
    private static int contadorId = 1;

    private final int id;
    private String nombre;
    private double precio;
    private int stock;

    public Producto(String nombre, double precio, int stock) {
        this.id = contadorId++;
        setNombre(nombre);
        setPrecio(precio);
        setStock(stock);
    }

    public int getId() {
        return id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        if (nombre == null || nombre.trim().isEmpty()) {
            throw new IllegalArgumentException("El nombre no puede estar vacío.");
        }
        this.nombre = nombre.trim();
    }

    public double getPrecio() {
        return precio;
    }

    public void setPrecio(double precio) {
        if (precio < 0) {
            throw new IllegalArgumentException("El precio no puede ser negativo.");
        }
        this.precio = precio;
    }

    public int getStock() {
        return stock;
    }

    public void setStock(int stock) {
        if (stock < 0) {
            throw new IllegalArgumentException("El stock no puede ser negativo.");
        }
        this.stock = stock;
    }

    /** Devuelve true si hay al menos 'cantidad' unidades disponibles. */
    public boolean hayStock(int cantidad) {
        return cantidad > 0 && cantidad <= stock;
    }

    /** Descuenta unidades del stock (se usa al confirmar un pedido). */
    public void descontarStock(int cantidad) {
        setStock(this.stock - cantidad);
    }

    /** Tipo de producto. Las subclases lo sobrescriben (polimorfismo). */
    public String getTipo() {
        return "General";
    }

    /** Información adicional propia de cada tipo. Las subclases la sobrescriben. */
    public String getDetalleExtra() {
        return "";
    }

    @Override
    public String toString() {
        String texto = String.format("ID: %-3d | %-20s | $ %10.2f | Stock: %-4d | %-8s",
                id, nombre, precio, stock, getTipo());
        String extra = getDetalleExtra();
        if (!extra.isEmpty()) {
            texto += " | " + extra;
        }
        return texto;
    }
}
