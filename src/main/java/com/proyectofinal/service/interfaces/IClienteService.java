package com.proyectofinal.service.interfaces;

import com.proyectofinal.controller.dto.ClienteDTO;

import java.util.List;
import java.util.Optional;

public interface IClienteService {

    List<ClienteDTO> findAll();

    ClienteDTO findById(Long id);

    void save(ClienteDTO clienteDTO);

    void deleteById(Long id);
}
