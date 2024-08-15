package com.proyectofinal.service.impl;

import com.proyectofinal.entities.VentaProducto;
import com.proyectofinal.persistence.interfaces.IVentaProductoDAO;
import com.proyectofinal.service.interfaces.IVentaProductoService;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;
import java.util.Optional;

public class VentaProductoServiceImpl implements IVentaProductoService {

    @Autowired
    private IVentaProductoDAO ventaProductoDAO;

    @Override
    public List<VentaProducto> findAll() {
        return this.ventaProductoDAO.findAll();
    }

    @Override
    public Optional<VentaProducto> findById(Long id) {
        return this.ventaProductoDAO.findById(id);
    }

    @Override
    public void save(VentaProducto ventaProducto) {
        this.ventaProductoDAO.save(ventaProducto);
    }

    @Override
    public void deleteById(Long id) {
        this.ventaProductoDAO.deleteById(id);
    }
}
