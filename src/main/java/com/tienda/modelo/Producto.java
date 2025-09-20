package com.tienda.modelo;

/**
 * Producto con stock y estado
 */
public class Producto {
    private int id;
    private String nombre;
    private double precio;
    private int stock;
    private boolean activo;

    public Producto() {}

    public Producto(int id, String nombre, double precio, int stock, boolean activo) {
        this.id = id;
        this.nombre = nombre;
        this.precio = precio;
        this.stock = stock;
        this.activo = activo;
    }

    public int getId() { return id; }
    public void setId(int id) { this.id = id; }

    public String getNombre() { return nombre; }
    public void setNombre(String nombre) { this.nombre = nombre; }

    public double getPrecio() { return precio; }
    public void setPrecio(double precio) { this.precio = precio; }

    public int getStock() { return stock; }
    public void setStock(int stock) { this.stock = stock; }

    public boolean isActivo() { return activo; }
    public void setActivo(boolean activo) { this.activo = activo; }

    @Override
    public String toString() {
        return id + ". " + nombre + " - $" + precio + " (stock=" + stock + ", activo=" + activo + ")";
    }
}
