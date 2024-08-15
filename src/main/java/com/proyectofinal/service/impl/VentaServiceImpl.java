package com.proyectofinal.service.impl;

import com.proyectofinal.entities.Producto;
import com.proyectofinal.entities.Venta;
import com.proyectofinal.persistence.interfaces.IVentaDAO;
import com.proyectofinal.service.interfaces.IVentaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Service
public class VentaServiceImpl implements IVentaService {

    @Autowired
    private IVentaDAO ventaDAO;

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

        double total = 0;
        for(Producto producto : venta.getListaProductos()){

            total += producto.getCosto();
        }
        LocalDate fecha = LocalDate.now();

        venta.setFecha_venta(fecha);
        venta.setTotal(total);

        this.ventaDAO.save(venta);
    }

    @Override
    public void deleteById(Long id) {
        this.ventaDAO.deleteById(id);
    }
}
