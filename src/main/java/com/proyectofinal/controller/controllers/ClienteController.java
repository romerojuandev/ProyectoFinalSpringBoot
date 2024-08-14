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

@RestController
@RequestMapping("/cliente")
public class ClienteController {

    @Autowired
    private IClienteService clienteService;
    private final ModelMapper modelMapper = new ModelMapper();

    @GetMapping("/find/{id}")
    public ResponseEntity<ClienteDTO> findById(@PathVariable Long id){

        ClienteDTO clienteDTO = this.clienteService.findById(id);

        return ResponseEntity.ok(clienteDTO);
    }

    @GetMapping("/find")
    public ResponseEntity<List<ClienteDTO>> findAll(){

        return ResponseEntity.ok(this.clienteService.findAll());
    }

    @PostMapping("/save")
    public ResponseEntity<ClienteDTO> save(@RequestBody ClienteDTO clienteDTO) throws URISyntaxException {

        if(clienteDTO.getNombre().isBlank() || clienteDTO.getApellido().isBlank() || clienteDTO.getDni().isBlank()){

            return ResponseEntity.badRequest().build();
        }

        this.clienteService.save(clienteDTO);

        return ResponseEntity.created(new URI("/cliente/save")).build();
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<ClienteDTO> updatecliente(@RequestBody ClienteDTO clienteDTO, @PathVariable Long id){

        if(id == null){

            return ResponseEntity.badRequest().build();
        }

        Cliente cliente = this.modelMapper.map(this.clienteService.findById(id), Cliente.class);

        if(cliente.getIdCliente() != null){

            ClienteDTO clienteActualizado = ClienteDTO.builder()
                    .idCliente(id)
                    .apellido(clienteDTO.getApellido())
                    .nombre(clienteDTO.getNombre())
                    .dni(clienteDTO.getDni())
                    .build();

            this.clienteService.save(clienteActualizado);

            return ResponseEntity.ok(clienteDTO);
        } else {

            return ResponseEntity.badRequest().build();
        }

    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<String> deleteById(@PathVariable Long id){

        if(id == null){

            return ResponseEntity.badRequest().build();
        }

        Cliente cliente = this.modelMapper.map(this.clienteService.findById(id), Cliente.class);

        if(cliente.getIdCliente() != null){
            this.clienteService.deleteById(id);

            return ResponseEntity.ok("Cliente eliminado");
        } else{

            return ResponseEntity.badRequest().build();
        }
    }
}
