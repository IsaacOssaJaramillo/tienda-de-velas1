package com.tienda.modelo;

/**
 * Clase abstracta Persona: base para Cliente y Administrador
 */
public abstract class Persona {
    protected String nombre;
    protected String clave;

    public Persona() {}

    public Persona(String nombre, String clave) {
        this.nombre = nombre;
        this.clave = clave;
    }

    public String getNombre() { return nombre; }
    public void setNombre(String nombre) { this.nombre = nombre; }

    public String getClave() { return clave; }
    public void setClave(String clave) { this.clave = clave; }
}
