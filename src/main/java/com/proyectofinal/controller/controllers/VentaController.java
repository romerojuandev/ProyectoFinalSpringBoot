package com.proyectofinal.controller.controllers;

import com.proyectofinal.controller.dto.VentaAux;
import com.proyectofinal.controller.dto.VentaDTO;
import com.proyectofinal.entities.Venta;
import com.proyectofinal.service.interfaces.IVentaService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/venta")
public class VentaController {

    @Autowired
    private IVentaService ventaService;
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
    public ResponseEntity<VentaDTO> save(@RequestBody VentaAux ventaAux) throws URISyntaxException {

        this.ventaService.procesarVenta(ventaAux.getCliente(), ventaAux.getProductos());

        return ResponseEntity.created(new URI("/venta/save")).build();
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<VentaAux> update(@RequestBody VentaAux ventaAux, @PathVariable Long id){

        this.ventaService.actulizarVenta(ventaAux.getCliente(), ventaAux.getProductos(), id);

        return ResponseEntity.ok(ventaAux);
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<String> deleteById(@PathVariable Long id){

        this.ventaService.deleteById(id);

        return ResponseEntity.ok("Venta eliminada");
    }
}
