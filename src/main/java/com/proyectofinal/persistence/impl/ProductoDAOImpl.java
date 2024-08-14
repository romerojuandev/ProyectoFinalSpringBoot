package com.proyectofinal.persistence.impl;

import com.proyectofinal.entities.Producto;
import com.proyectofinal.persistence.interfaces.IProductoDAO;
import com.proyectofinal.repository.ProductoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Optional;

@Repository
public class ProductoDAOImpl implements IProductoDAO {

    @Autowired
    private ProductoRepository productoRepository;

    @Override
    public List<Producto> findAll() {
        return (List<Producto>) this.productoRepository.findAll();
    }

    @Override
    public Optional<Producto> findById(Long id) {
        return this.productoRepository.findById(id);
    }

    @Override
    public void save(Producto producto) {
        this.productoRepository.save(producto);
    }

    @Override
    public void deleteById(Long id) {
        this.productoRepository.deleteById(id);
    }
}
