package com.tienda.servicio;

import com.tienda.modelo.Producto;
import com.tienda.repositorio.IProductoRepo;
import com.tienda.repositorio.ProductoRepoMemoria;

import java.util.List;

/**
 * Servicio para administración de productos
 */
public class AdminService implements IAdminService {
    private IProductoRepo repo = new ProductoRepoMemoria();

    @Override
    public void agregarProducto(Producto p) {
        repo.save(p);
    }

    @Override
    public List<Producto> listarProductos() {
        return repo.findAll();
    }

    @Override
    public Producto obtenerProducto(int id) {
        return repo.findById(id);
    }

    @Override
    public void editarProducto(Producto p) {
        repo.update(p);
    }

    @Override
    public void eliminarProducto(int id) {
        repo.delete(id);
    }
}
