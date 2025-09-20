package com.tienda.servicio;

import com.tienda.modelo.Producto;
import java.util.List;

public interface IAdminService {
    void agregarProducto(Producto p);
    List<Producto> listarProductos();
    Producto obtenerProducto(int id);
    void editarProducto(Producto p);
    void eliminarProducto(int id);
}
