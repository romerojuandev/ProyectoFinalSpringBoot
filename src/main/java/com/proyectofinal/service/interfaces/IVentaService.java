package com.proyectofinal.service.interfaces;

import com.proyectofinal.entities.Venta;

import java.util.List;
import java.util.Optional;

public interface IVentaService {

    List<Venta> findAll();

    Optional<Venta> findById(Long id);

    void save(Venta venta);

    void deleteById(Long id);
}
