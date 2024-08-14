package com.proyectofinal.controller.controllers;

import com.proyectofinal.controller.dto.ClienteDTO;
import com.proyectofinal.entities.Cliente;
import com.proyectofinal.service.interfaces.IClienteService;
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
@RequestMapping("/cliente")
public class ClienteController {

    @Autowired
    private IClienteService clienteService;
    private final ModelMapper modelMapper = new ModelMapper();

    @GetMapping("/find/{id}")
    public ResponseEntity<ClienteDTO> findById(@PathVariable Long id){

        Optional<Cliente> clienteOptional = this.clienteService.findById(id);

        if(clienteOptional.isPresent()){

            Cliente cliente = clienteOptional.get();

            ClienteDTO clienteDTO = this.modelMapper.map(cliente, ClienteDTO.class);

            return ResponseEntity.ok(clienteDTO);
        }

        return ResponseEntity.badRequest().build();
    }

    @GetMapping("/find")
    public ResponseEntity<List<ClienteDTO>> findAll(){

        List<ClienteDTO> clienteDTOList = this.clienteService.findAll()
                .stream()
                .map(cliente -> this.modelMapper.map(cliente, ClienteDTO.class))
                .collect(Collectors.toList());

        return ResponseEntity.ok(clienteDTOList);
    }

    @PostMapping("/save")
    public ResponseEntity<ClienteDTO> save(@RequestBody ClienteDTO clienteDTO) throws URISyntaxException {

        if(clienteDTO.getNombre().isBlank() || clienteDTO.getApellido().isBlank() || clienteDTO.getDni().isBlank()){

            return ResponseEntity.badRequest().build();
        }

        this.clienteService.save(this.modelMapper.map(clienteDTO, Cliente.class));

        return ResponseEntity.created(new URI("/cliente/save")).build();
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<ClienteDTO> updatecliente(@RequestBody ClienteDTO clienteDTO, @PathVariable Long id){

        if(id == null){

            return ResponseEntity.badRequest().build();
        }

        Optional<Cliente> clienteOptional = this.clienteService.findById(id);

        if(clienteOptional.isPresent()){

            Cliente cliente = clienteOptional.get();
            cliente.setApellido(clienteDTO.getApellido());
            cliente.setNombre(clienteDTO.getNombre());
            cliente.setDni(clienteDTO.getDni());
            cliente.setVentaList(clienteDTO.getVentaList());

            this.clienteService.save(cliente);

            return ResponseEntity.ok(clienteDTO);
        }

        return ResponseEntity.badRequest().build();

    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<String> deleteById(@PathVariable Long id){

        if(id == null){

            return ResponseEntity.badRequest().build();
        }

        Optional<Cliente> clienteOptional = this.clienteService.findById(id);

        if(clienteOptional.isPresent()){

            this.clienteService.deleteById(id);

            return ResponseEntity.ok("Cliente eliminado");
        } else{

            return ResponseEntity.ok("El cliente no existe");
        }
    }
}
