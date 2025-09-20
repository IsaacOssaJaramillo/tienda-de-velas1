package com.tienda.servicio;

import com.tienda.modelo.Cliente;
import com.tienda.modelo.Pedido;

public interface IClienteService {
    void registrarCliente(Cliente c);
    Cliente login(String nombre, String clave);
    void realizarPedido(Cliente c, Pedido pedido);
}
