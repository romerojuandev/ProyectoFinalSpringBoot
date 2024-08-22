package com.proyectofinal.controller.controllers;

import com.proyectofinal.controller.dto.ProductoDTO;
import com.proyectofinal.controller.dto.VentaDTO;
import com.proyectofinal.controller.dto.VentaMayorDTO;
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

    @GetMapping("productos/{codigo}")
    public ResponseEntity<List<ProductoDTO>> listadoProductos(@PathVariable Long codigo){

        Optional<Venta> ventaOptional = this.ventaService.findById(codigo);

        if (ventaOptional.isPresent()){

            Venta venta = ventaOptional.get();
            List<ProductoDTO> productoDTOList = venta.getListaProductos()
                    .stream()
                    .map(producto -> this.modelMapper.map(producto, ProductoDTO.class))
                    .collect(Collectors.toList());

            return ResponseEntity.ok(productoDTOList);
        }

        return ResponseEntity.badRequest().build();
    }

    @GetMapping("/{fecha_venta}")
    public ResponseEntity<String> totalVentas(@PathVariable LocalDate fecha_venta){

        List<Venta> ventaList = this.ventaService.findAll();
        double total = 0;
        int cantidad = 0;

        for (Venta venta : ventaList){

            if (venta.getFecha_venta().equals(fecha_venta)){

                total += venta.getTotal();
                cantidad ++;
            }
        }

        if(cantidad > 0){

            return  ResponseEntity.ok("El total recaudado el día " + fecha_venta + ", es de: $" + total + ", la cantidad de ventas fue de " + cantidad);
        } else {

            return   ResponseEntity.ok("El día " + fecha_venta + " no hubo ventas.");
        }

    }

    @GetMapping("/mayor_venta")
    public ResponseEntity<VentaMayorDTO> mayorVenta(){

        List<Venta> ventaList = this.ventaService.findAll();
        Venta mayorVenta = new Venta();

        for (Venta venta : ventaList){

            if (venta.getTotal() > mayorVenta.getTotal()){

                mayorVenta = venta;
            }
        }

        int contador = 0;

        for (Producto producto : mayorVenta.getListaProductos()){

            contador ++;
        }

        VentaMayorDTO ventaMayorDTO = VentaMayorDTO.builder()
                .codigoVenta(mayorVenta.getCodigo_venta())
                .total(mayorVenta.getTotal())
                .cantidadProductos(contador)
                .apellidoCliente(mayorVenta.getCliente().getApellido())
                .nombreCliente(mayorVenta.getCliente().getNombre())
                .build();

        return ResponseEntity.ok(ventaMayorDTO);
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
