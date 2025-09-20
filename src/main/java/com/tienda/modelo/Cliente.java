package com.tienda.modelo;

import java.util.ArrayList;
import java.util.List;

/**
 * Cliente con historial de pedidos
 */
public class Cliente extends Persona {
    private int id;
    private List<Pedido> pedidos = new ArrayList<>();

    public Cliente() { super(); }

    public Cliente(int id, String nombre, String clave) {
        super(nombre, clave);
        this.id = id;
    }

    public int getId() { return id; }
    public void setId(int id) { this.id = id; }

    public void agregarPedido(Pedido pedido) { pedidos.add(pedido); }
    public List<Pedido> getPedidos() { return pedidos; }
}
