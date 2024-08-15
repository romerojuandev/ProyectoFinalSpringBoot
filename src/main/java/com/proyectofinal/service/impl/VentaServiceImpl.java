package com.proyectofinal.service.impl;

import com.proyectofinal.entities.Cliente;
import com.proyectofinal.entities.Producto;
import com.proyectofinal.entities.Venta;
import com.proyectofinal.entities.VentaProducto;
import com.proyectofinal.persistence.interfaces.IProductoDAO;
import com.proyectofinal.persistence.interfaces.IVentaDAO;
import com.proyectofinal.persistence.interfaces.IVentaProductoDAO;
import com.proyectofinal.service.interfaces.IVentaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class VentaServiceImpl implements IVentaService {

    @Autowired
    private IVentaDAO ventaDAO;
    @Autowired
    private IProductoDAO productoDAO;
    @Autowired
    private IVentaProductoDAO ventaProductoDAO;

    @Override
    public List<Venta> findAll() {
        return this.ventaDAO.findAll();

    }

    @Override
    public Optional<Venta> findById(Long id) {
        return this.ventaDAO.findById(id);
    }

    @Override
    public void deleteById(Long id) {
        this.ventaDAO.deleteById(id);
    }

    @Override
    public void save(Venta venta) {

        this.ventaDAO.save(venta);
    }

    public void procesarVenta(Cliente cliente, List<Producto> productos) {
        List<VentaProducto> ventaProductos = new ArrayList<>();
        double total = 0;

        for (Producto producto : productos) {
            Producto p = this.productoDAO.findById(producto.getCodigoProducto())
                    .orElseThrow(() -> new IllegalArgumentException("Producto no encontrado: " + producto.getCodigoProducto()));

            if (p.getCantidadDisponible() < producto.getCantidadSolicitada()) {
                throw new IllegalArgumentException("Stock insuficiente para el producto: " + p.getNombre());
            }

            p.setCantidadDisponible(p.getCantidadDisponible() - producto.getCantidadSolicitada());
            productoDAO.save(p);

            VentaProducto ventaProducto = new VentaProducto(null, p, producto.getCantidadSolicitada());
            ventaProductos.add(ventaProducto);

            total += p.getCosto() * producto.getCantidadSolicitada();
        }

        Venta venta = new Venta(cliente, ventaProductos, total);
        venta.setFecha_venta(LocalDate.now());
        ventaDAO.save(venta);

        for (VentaProducto vp : ventaProductos) {
            vp.setVenta(venta);
            ventaProductoDAO.save(vp);
        }
    }
}

