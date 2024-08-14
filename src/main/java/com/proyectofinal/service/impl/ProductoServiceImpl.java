package com.proyectofinal.service.impl;

import com.proyectofinal.entities.Producto;
import com.proyectofinal.persistence.interfaces.IProductoDAO;
import com.proyectofinal.service.interfaces.IProductoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ProductoServiceImpl implements IProductoService {

    @Autowired
    private IProductoDAO productoDAO;

    @Override
    public List<Producto> findAll() {
        return this.productoDAO.findAll();
    }

    @Override
    public Optional<Producto> findById(Long id) {
        return this.productoDAO.findById(id);
    }

    @Override
    public void save(Producto producto) {
        this.productoDAO.save(producto);
    }

    @Override
    public void deleteById(Long id) {
        this.productoDAO.deleteById(id);
    }
}
