package com.techlab.productos;

/**
 * Subclase de Producto (herencia) con un atributo propio: la fecha de vencimiento.
 */
public class Comida extends Producto {

    private String fechaVencimiento; // formato libre, por ejemplo "31/12/2026"

    public Comida(String nombre, double precio, int stock, String fechaVencimiento) {
        super(nombre, precio, stock);
        setFechaVencimiento(fechaVencimiento);
    }

    public String getFechaVencimiento() {
        return fechaVencimiento;
    }

    public void setFechaVencimiento(String fechaVencimiento) {
        if (fechaVencimiento == null || fechaVencimiento.trim().isEmpty()) {
            throw new IllegalArgumentException("La fecha de vencimiento no puede estar vacía.");
        }
        this.fechaVencimiento = fechaVencimiento.trim();
    }

    @Override
    public String getTipo() {
        return "Comida";
    }

    @Override
    public String getDetalleExtra() {
        return "Vence: " + fechaVencimiento;
    }
}
