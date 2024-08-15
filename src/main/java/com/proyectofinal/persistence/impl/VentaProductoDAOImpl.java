package com.proyectofinal.persistence.impl;

import com.proyectofinal.entities.VentaProducto;
import com.proyectofinal.persistence.interfaces.IVentaProductoDAO;
import com.proyectofinal.repository.VentaProductoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public class VentaProductoDAOImpl implements IVentaProductoDAO {

    @Autowired
    private VentaProductoRepository ventaProductoRepository;

    @Override
    public List<VentaProducto> findAll() {
        return (List<VentaProducto>) this.ventaProductoRepository.findAll();
    }

    @Override
    public Optional<VentaProducto> findById(Long id) {
        return this.ventaProductoRepository.findById(id);
    }

    @Override
    public void save(VentaProducto ventaProducto) {
        this.ventaProductoRepository.save(ventaProducto);
    }

    @Override
    public void deleteById(Long id) {
        this.ventaProductoRepository.deleteById(id);
    }
}
