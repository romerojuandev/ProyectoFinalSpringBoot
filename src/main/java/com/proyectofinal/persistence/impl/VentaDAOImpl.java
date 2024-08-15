package com.proyectofinal.persistence.impl;

import com.proyectofinal.entities.Venta;
import com.proyectofinal.persistence.interfaces.IVentaDAO;
import com.proyectofinal.repository.VentaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public class VentaDAOImpl implements IVentaDAO {

    @Autowired
    private VentaRepository ventaRepository;

    @Override
    public List<Venta> findAll() {
        return (List<Venta>) this.ventaRepository.findAll();
    }

    @Override
    public Optional<Venta> findById(Long id) {
        return this.ventaRepository.findById(id);
    }

    @Override
    public void save(Venta venta) {
        this.ventaRepository.save(venta);
    }

    @Override
    public void deleteById(Long id) {
        this.ventaRepository.deleteById(id);
    }
}
