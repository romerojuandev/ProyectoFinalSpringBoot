package com.proyectofinal.controller.controllers;

import com.proyectofinal.controller.dto.ProductoDTO;
import com.proyectofinal.entities.Producto;
import com.proyectofinal.service.interfaces.IProductoService;
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
@RequestMapping("/producto")
public class ProductoController {

    @Autowired
    private IProductoService productoService;
    private final ModelMapper modelMapper = new ModelMapper();

    @GetMapping("/find/{id}")
    public ResponseEntity<ProductoDTO> findById(@PathVariable Long id){

        Optional<Producto> productoOptional = this.productoService.findById(id);

        if(productoOptional.isPresent()){

            Producto producto = productoOptional.get();

            ProductoDTO productoDTO = this.modelMapper.map(producto, ProductoDTO.class);

            return ResponseEntity.ok(productoDTO);
        }

        return ResponseEntity.badRequest().build();
    }

    @GetMapping("/find")
    public ResponseEntity<List<ProductoDTO>> findAll(){

        List<ProductoDTO> productoList = this.productoService.findAll()
                .stream()
                .map(producto -> this.modelMapper.map(producto, ProductoDTO.class))
                .collect(Collectors.toList());

        return ResponseEntity.ok(productoList);
    }

    @PostMapping("/save")
    public ResponseEntity<ProductoDTO> save(@RequestBody ProductoDTO productoDTO) throws URISyntaxException {

        if(productoDTO.getNombre().isBlank() || productoDTO.getMarca().isBlank()){

            return ResponseEntity.badRequest().build();
        }

        this.productoService.save(this.modelMapper.map(productoDTO, Producto.class));

        return ResponseEntity.created(new URI("/producto/save")).build();
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<ProductoDTO> updateProducto(@RequestBody ProductoDTO productoDTO, @PathVariable Long id){

        Optional<Producto> productoOptional = this.productoService.findById(id);

        if(productoOptional.isPresent()){

            Producto producto = productoOptional.get();
            producto.setNombre(productoDTO.getNombre());
            producto.setMarca(productoDTO.getMarca());
            producto.setCosto(productoDTO.getCosto());
            producto.setCantidadDisponible(productoDTO.getCantidadDisponible());

            this.productoService.save(producto);

            return ResponseEntity.ok(productoDTO);
        }

        return ResponseEntity.badRequest().build();
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<String> deleteById(@PathVariable Long id){

        Optional<Producto> productoOptional = this.productoService.findById(id);

        if(productoOptional.isPresent()){

            this.productoService.deleteById(id);

            return ResponseEntity.ok("Producto eliminado");
        } else {

            return ResponseEntity.ok("El producto no existe");
        }
    }
}
