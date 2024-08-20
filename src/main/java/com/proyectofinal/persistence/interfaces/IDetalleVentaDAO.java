package com.proyectofinal.persistence.interfaces;

import com.proyectofinal.entities.DetalleVenta;

import java.util.List;
import java.util.Optional;

public interface IDetalleVentaDAO {

    List<DetalleVenta> findAll();

    Optional<DetalleVenta> findById(Long id);

    void save(DetalleVenta detalleVenta);

    void saveAll(List<DetalleVenta> detalleVentaList);

    void deleteById(Long id);
}
