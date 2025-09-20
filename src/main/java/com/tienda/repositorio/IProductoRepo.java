package com.tienda.repositorio;

import com.tienda.modelo.Producto;
import java.util.List;

public interface IProductoRepo {
    void save(Producto p);
    List<Producto> findAll();
    Producto findById(int id);
    void update(Producto p);
    void delete(int id);
}
