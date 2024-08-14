package com.proyectofinal.persistence.interfaces;

import com.proyectofinal.entities.Cliente;

import java.util.List;
import java.util.Optional;

public interface IClienteDAO {

    List<Cliente> findAll();

    Optional<Cliente> findById(Long id);

    void save(Cliente cliente);

    void deleteById(Long id);
}
