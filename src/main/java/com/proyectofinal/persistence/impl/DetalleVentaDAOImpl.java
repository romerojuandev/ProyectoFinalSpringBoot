package com.proyectofinal.persistence.impl;

import com.proyectofinal.entities.DetalleVenta;
import com.proyectofinal.persistence.interfaces.IDetalleVentaDAO;
import com.proyectofinal.repository.DetalleVentaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public class DetalleVentaDAOImpl implements IDetalleVentaDAO {

    @Autowired
    private DetalleVentaRepository detalleVentaRepository;

    @Override
    public List<DetalleVenta> findAll() {
        return (List<DetalleVenta>) this.detalleVentaRepository.findAll();
    }

    @Override
    public Optional<DetalleVenta> findById(Long id) {
        return this.detalleVentaRepository.findById(id);
    }

    @Override
    public void save(DetalleVenta detalleVenta) {
        this.detalleVentaRepository.save(detalleVenta);
    }

    @Override
    public void saveAll(List<DetalleVenta> detalleVentaList) {
        this.detalleVentaRepository.saveAll(detalleVentaList);
    }

    @Override
    public void deleteById(Long id) {
        this.detalleVentaRepository.deleteById(id);
    }
}
