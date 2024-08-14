package com.proyectofinal.service.interfaces;

import com.proyectofinal.controller.dto.ClienteDTO;
import com.proyectofinal.entities.Cliente;

import java.util.List;
import java.util.Optional;

public interface IClienteService {

    List<Cliente> findAll();

    Optional<Cliente> findById(Long id);

    void save(Cliente cliente);

    void deleteById(Long id);
}
