package com.techlab.productos;

/**
 * Subclase de Producto (herencia) con un atributo propio: el volumen en litros.
 */
public class Bebida extends Producto {

    private double volumenLitros;

    public Bebida(String nombre, double precio, int stock, double volumenLitros) {
        super(nombre, precio, stock);
        setVolumenLitros(volumenLitros);
    }

    public double getVolumenLitros() {
        return volumenLitros;
    }

    public void setVolumenLitros(double volumenLitros) {
        if (volumenLitros <= 0) {
            throw new IllegalArgumentException("El volumen debe ser mayor a 0.");
        }
        this.volumenLitros = volumenLitros;
    }

    @Override
    public String getTipo() {
        return "Bebida";
    }

    @Override
    public String getDetalleExtra() {
        return String.format("Volumen: %.2f L", volumenLitros);
    }
}
