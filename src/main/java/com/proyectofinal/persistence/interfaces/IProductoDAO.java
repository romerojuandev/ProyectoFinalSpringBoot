package com.proyectofinal.persistence.interfaces;

import com.proyectofinal.entities.Producto;

import java.util.List;
import java.util.Optional;

public interface IProductoDAO {

    List<Producto> findAll();

    Optional<Producto> findById(Long id);

    void save(Producto producto);

    void deleteById(Long id);
}
