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

//    public boolean validarStock(List<Producto> productos) {
//        for (Producto producto : productos) {
//            Producto p = this.productoDAO.findById(producto.getCodigoProducto())
//                    .orElseThrow(() -> new IllegalArgumentException("Producto no encontrado: " + producto.getCodigoProducto()));
//
//            if (p.getCantidadDisponible() < producto.getCantidadSolicitada()) {
//                return false;
//            }
//        }
//        return true;
//    }
//
//    // Método para procesar la venta
//    public double procesarVenta(List<Producto> productos) {
//        if (!validarStock(productos)) {
//            throw new IllegalArgumentException("Stock insuficiente para uno o más productos");
//        }
//
//        double total = 0;
//
//        for (Producto producto : productos) {
//            Producto p = this.productoDAO.findById(producto.getCodigoProducto())
//                    .orElseThrow(() -> new IllegalArgumentException("Producto no encontrado: " + producto.getCodigoProducto()));
//
//            total += (p.getCosto() * producto.getCantidadSolicitada());
//            p.setCantidadDisponible(p.getCantidadDisponible() - producto.getCantidadSolicitada());
//            this.productoDAO.save(p);
//        }
//
//        return total;
//        // Lógica adicional para completar la venta...
//    }
}
