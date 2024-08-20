package com.proyectofinal.controller.controllers;

import com.proyectofinal.controller.dto.VentaDTO;
import com.proyectofinal.entities.DetalleVenta;
import com.proyectofinal.entities.Producto;
import com.proyectofinal.entities.Venta;
import com.proyectofinal.service.interfaces.IDetalleVentaService;
import com.proyectofinal.service.interfaces.IVentaService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.net.URISyntaxException;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/venta")
public class VentaController {

    @Autowired
    private IVentaService ventaService;
    @Autowired
    private IDetalleVentaService detalleVentaService;
    private final ModelMapper modelMapper = new ModelMapper();

    @GetMapping("/find")
    public ResponseEntity<List<VentaDTO>> findAll(){

        List<VentaDTO> ventaDTOList = this.ventaService.findAll()
                .stream()
                .map(venta -> this.modelMapper.map(venta, VentaDTO.class))
                .collect(Collectors.toList());

        return ResponseEntity.ok(ventaDTOList);
    }

    @GetMapping("/find/{id}")
    public  ResponseEntity<VentaDTO> findById(@PathVariable Long id){

        Optional<Venta> ventaOptional = this.ventaService.findById(id);

        if(ventaOptional.isPresent()){

            Venta venta = ventaOptional.get();
            VentaDTO ventaDTO = this.modelMapper.map(venta, VentaDTO.class);

            return ResponseEntity.ok(ventaDTO);
        }

        return ResponseEntity.badRequest().build();
    }

    @PostMapping("/save")
    public ResponseEntity<VentaDTO> save(@RequestBody VentaDTO ventaDTO) throws URISyntaxException {

        Venta venta = Venta.builder()
                .fecha_venta(LocalDate.now())
                .cliente(ventaDTO.getCliente())
                .listaProductos(ventaDTO.getListaProductos())
                .build();

        this.ventaService.validarStock(venta.getListaProductos());
        double total = this.ventaService.calcularTotal(venta.getListaProductos());
        this.ventaService.restarStock(venta.getListaProductos());
        venta.setTotal(total);

        this.ventaService.save(venta);

        for(Producto producto : venta.getListaProductos()){

            this.detalleVentaService.save(DetalleVenta.builder()
                    .idVenta(venta.getCodigo_venta())
                    .idProducto(producto.getCodigoProducto())
                    .cantidad(producto.getCantidadSolicitada())
                    .build()
            );
        }

        return ResponseEntity.created(new URI("/venta/save")).build();
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<VentaDTO> update(@RequestBody VentaDTO ventaDTO, @PathVariable Long id){

        Optional<Venta> ventaOptional = this.ventaService.findById(id);

        if(ventaOptional.isPresent()){
            Venta venta = ventaOptional.get();
            venta.setFecha_venta(LocalDate.now());
            venta.setCliente(ventaDTO.getCliente());
            venta.setListaProductos(ventaDTO.getListaProductos());
            double total = this.ventaService.calcularTotal(venta.getListaProductos());
            venta.setTotal(total);

            this.ventaService.devolverStock(id);
            this.ventaService.validarStock(venta.getListaProductos());
            this.ventaService.restarStock(venta.getListaProductos());


            for(Producto producto : venta.getListaProductos()){

                this.detalleVentaService.save(DetalleVenta.builder()
                        .idVenta(venta.getCodigo_venta())
                        .idProducto(producto.getCodigoProducto())
                        .cantidad(producto.getCantidadSolicitada())
                        .build()
                );
            }

            this.ventaService.save(venta);

            return ResponseEntity.ok(ventaDTO);
        }

        return ResponseEntity.badRequest().build();
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<String> deleteById(@PathVariable Long id){

        Optional<Venta> ventaOptional = this.ventaService.findById(id);

        if(ventaOptional.isPresent()){

            this.ventaService.deleteById(id);
        }

        return ResponseEntity.ok("Venta eliminada");
    }
}
