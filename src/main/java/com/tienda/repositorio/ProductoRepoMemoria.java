package com.tienda.repositorio;

import com.tienda.modelo.Producto;
import java.util.ArrayList;
import java.util.List;

/**
 * Repositorio en memoria para productos
 */
public class ProductoRepoMemoria implements IProductoRepo {
    private List<Producto> productos = new ArrayList<>();
    private int sequence = 1;

    @Override
    public void save(Producto p) {
        p.setId(sequence++);
        productos.add(p);
    }

    @Override
    public List<Producto> findAll() {
        return new ArrayList<>(productos);
    }

    @Override
    public Producto findById(int id) {
        return productos.stream().filter(x -> x.getId() == id).findFirst().orElse(null);
    }

    @Override
    public void update(Producto p) {
        Producto ex = findById(p.getId());
        if (ex != null) {
            ex.setNombre(p.getNombre());
            ex.setPrecio(p.getPrecio());
            ex.setStock(p.getStock());
            ex.setActivo(p.isActivo());
        }
    }

    @Override
    public void delete(int id) {
        productos.removeIf(x -> x.getId() == id);
    }
}
