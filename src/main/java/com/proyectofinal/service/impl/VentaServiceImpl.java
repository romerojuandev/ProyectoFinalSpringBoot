package com.proyectofinal.service.impl;

import com.proyectofinal.entities.DetalleVenta;
import com.proyectofinal.entities.Producto;
import com.proyectofinal.entities.Venta;
import com.proyectofinal.persistence.interfaces.IDetalleVentaDAO;
import com.proyectofinal.persistence.interfaces.IProductoDAO;
import com.proyectofinal.persistence.interfaces.IVentaDAO;
import com.proyectofinal.service.interfaces.IVentaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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
    private IDetalleVentaDAO detalleVentaDAO;

    @Override
    public List<Venta> findAll() {
        return this.ventaDAO.findAll();

    }

    @Override
    public Optional<Venta> findById(Long id) {
        return this.ventaDAO.findById(id);
    }

    @Override
    public void save(Venta venta) {

        this.ventaDAO.save(venta);
    }


    public boolean validarStock(List<Producto> productos){

        for(Producto producto : productos) {
            Producto p = this.productoDAO.findById(producto.getCodigoProducto())
                    .orElseThrow(() -> new IllegalArgumentException("Producto no encontrado: " + producto.getCodigoProducto()));

            if (p.getCantidadDisponible() < producto.getCantidadSolicitada()) {
                return false;
            }
        }

        return true;
    }

    public double calcularTotal(List<Producto> productoList){

        double total = 0;

        for(Producto producto : productoList){

            Producto p = this.productoDAO.findById(producto.getCodigoProducto())
                    .orElseThrow(() -> new IllegalArgumentException("Producto no encontrado: " + producto.getCodigoProducto()));


            total += (p.getCosto() * producto.getCantidadSolicitada());
        }

        return total;
    }

    public void restarStock(List<Producto> productoList){

        for(Producto producto : productoList){

            Producto p = this.productoDAO.findById(producto.getCodigoProducto())
                    .orElseThrow(() -> new IllegalArgumentException("Producto no encontrado: " + producto.getCodigoProducto()));

            p.setCantidadDisponible(p.getCantidadDisponible() - producto.getCantidadSolicitada());

            this.productoDAO.save(p);
        }
    }

    @Override
    public void devolverStock(Long id){

        List<DetalleVenta> detalleVentaList = this.detalleVentaDAO.findAll();
        List<DetalleVenta> listaFiltrada = new ArrayList<>();

        for (DetalleVenta detalle : detalleVentaList){

            if(detalle.getIdVenta() == id){
                listaFiltrada.add(detalle);
            }

            this.detalleVentaDAO.deleteById(detalle.getId());
        }

        for (DetalleVenta detalleVenta : listaFiltrada){
            Producto p = this.productoDAO.findById(detalleVenta.getIdProducto())
                    .orElseThrow(() -> new IllegalArgumentException("Producto no encontrado: " + detalleVenta.getIdProducto()));

            p.setCantidadDisponible(p.getCantidadDisponible() + detalleVenta.getCantidad());
            productoDAO.save(p);
        }
    }

    @Override
    public void deleteById(Long id){

        devolverStock(id);

        this.ventaDAO.deleteById(id);
    }
}

