package com.proyectofinal.service.impl;

import com.proyectofinal.entities.DetalleVenta;
import com.proyectofinal.persistence.interfaces.IDetalleVentaDAO;
import com.proyectofinal.service.interfaces.IDetalleVentaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class DetalleVentaServiceImpl implements IDetalleVentaService {

    @Autowired
    private IDetalleVentaDAO detalleVentaDAO;

    @Override
    public List<DetalleVenta> findAll() {
        return this.detalleVentaDAO.findAll();
    }

    @Override
    public Optional<DetalleVenta> findById(Long id) {
        return this.detalleVentaDAO.findById(id);
    }

    @Override
    public void save(DetalleVenta detalleVenta) {
        this.detalleVentaDAO.save(detalleVenta);
    }

    @Override
    public void saveAll(List<DetalleVenta> detalleVentaList) {
        this.detalleVentaDAO.saveAll(detalleVentaList);
    }

    @Override
    public void deleteById(Long id) {
        this.detalleVentaDAO.deleteById(id);
    }
}
