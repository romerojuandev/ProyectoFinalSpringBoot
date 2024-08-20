package com.proyectofinal.service.interfaces;

import com.proyectofinal.entities.Cliente;
import com.proyectofinal.entities.DetalleVenta;
import com.proyectofinal.entities.Producto;
import com.proyectofinal.entities.Venta;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

public interface IVentaService {

    List<Venta> findAll();

    Optional<Venta> findById(Long id);

    void save(Venta venta);

    void deleteById(Long id);

    boolean validarStock(List<Producto> productos);

    void restarStock(List<Producto> productoList);

    double calcularTotal(List<Producto> productoList);

    void devolverStock(Long id);
}
