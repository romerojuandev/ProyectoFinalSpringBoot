package com.proyectofinal.service.impl;

import com.proyectofinal.entities.Cliente;
import com.proyectofinal.persistence.interfaces.IClienteDAO;
import com.proyectofinal.service.interfaces.IClienteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ClienteServiceImpl implements IClienteService {

    @Autowired
    private IClienteDAO clienteDAO;

    @Override
    public List<Cliente> findAll() {

        return this.clienteDAO.findAll();
    }

    @Override
    public Optional<Cliente> findById(Long id) {

        return this.clienteDAO.findById(id);
    }

    @Override
    public void save(Cliente cliente) {

        this.clienteDAO.save(cliente);
    }

    @Override
    public void deleteById(Long id) {

        this.clienteDAO.deleteById(id);
    }


}
