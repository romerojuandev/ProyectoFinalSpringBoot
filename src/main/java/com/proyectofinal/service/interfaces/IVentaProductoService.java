package com.proyectofinal.service.interfaces;

import com.proyectofinal.entities.VentaProducto;

import java.util.List;
import java.util.Optional;

public interface IVentaProductoService {

    List<VentaProducto> findAll();

    Optional<VentaProducto> findById(Long id);

    void save(VentaProducto ventaProducto);

    void deleteById(Long id);
}
