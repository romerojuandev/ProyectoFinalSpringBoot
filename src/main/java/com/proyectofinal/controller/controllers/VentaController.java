package com.proyectofinal.controller.controllers;

import com.proyectofinal.controller.dto.VentaAux;
import com.proyectofinal.controller.dto.VentaDTO;
import com.proyectofinal.service.interfaces.IVentaService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
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

    @PostMapping("/save")
    public ResponseEntity<VentaDTO> save(@RequestBody VentaAux ventaAux) throws URISyntaxException {

        this.ventaService.procesarVenta(ventaAux.getCliente(), ventaAux.getProductos());

        return ResponseEntity.created(new URI("/venta/save")).build();
    }

}
