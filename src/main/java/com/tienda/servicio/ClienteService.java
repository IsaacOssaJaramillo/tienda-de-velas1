package com.tienda.servicio;

import com.tienda.modelo.Cliente;
import com.tienda.modelo.Pedido;
import java.util.ArrayList;
import java.util.List;

/**
 * Servicio para clientes (registro, login, pedidos) en memoria
 */
public class ClienteService implements IClienteService {
    private List<Cliente> clientes = new ArrayList<>();
    private int sequence = 1;

    @Override
    public void registrarCliente(Cliente c) {
        c.setId(sequence++);
        clientes.add(c);
    }

    @Override
    public Cliente login(String nombre, String clave) {
        return clientes.stream().filter(x -> x.getNombre().equals(nombre) && x.getClave().equals(clave)).findFirst().orElse(null);
    }

    @Override
    public void realizarPedido(Cliente c, Pedido pedido) {
        c.agregarPedido(pedido);
    }

    public List<Cliente> listarClientes() {
        return clientes;
    }
}
