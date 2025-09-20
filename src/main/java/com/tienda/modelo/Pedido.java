package com.tienda.modelo;

import java.time.LocalDateTime;
import java.util.Map;

/**
 * Pedido con fecha, productos y total
 */
public class Pedido {
    private int id;
    private LocalDateTime fecha;
    private Map<Producto, Integer> productos;
    private double total;

    public Pedido() {}

    public Pedido(int id, LocalDateTime fecha, Map<Producto, Integer> productos, double total) {
        this.id = id;
        this.fecha = fecha;
        this.productos = productos;
        this.total = total;
    }

    public int getId() { return id; }
    public LocalDateTime getFecha() { return fecha; }
    public Map<Producto, Integer> getProductos() { return productos; }
    public double getTotal() { return total; }
}
