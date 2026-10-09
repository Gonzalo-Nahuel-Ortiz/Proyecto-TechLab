package com.techlab.excepciones;

/**
 * Excepción personalizada: se lanza cuando se pide más cantidad de la que hay en stock.
 */
public class StockInsuficienteException extends Exception {

    public StockInsuficienteException(String mensaje) {
        super(mensaje);
    }
}
