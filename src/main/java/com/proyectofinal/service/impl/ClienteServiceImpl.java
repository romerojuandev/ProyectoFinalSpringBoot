package com.proyectofinal.service.impl;

import com.proyectofinal.controller.dto.ClienteDTO;
import com.proyectofinal.entities.Cliente;
import com.proyectofinal.persistence.interfaces.IClienteDAO;
import com.proyectofinal.service.interfaces.IClienteService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class ClienteServiceImpl implements IClienteService {

    @Autowired
    private IClienteDAO clienteDAO;
    private final ModelMapper modelMapper = new ModelMapper();

    @Override
    public List<ClienteDTO> findAll() {

        return this.clienteDAO.findAll()
                .stream()
                .map(cliente -> this.modelMapper.map(cliente, ClienteDTO.class))
                .collect(Collectors.toList());
    }

    @Override
    public ClienteDTO findById(Long id) {

        Optional<Cliente> clienteOptional = this.clienteDAO.findById(id);

        if(clienteOptional.isPresent()){
            Cliente cliente = clienteOptional.get();

            return this.modelMapper.map(cliente, ClienteDTO.class);
        }else{
            return new ClienteDTO();
        }
    }

    @Override
    public void save(ClienteDTO clienteDTO) {
        try{
            Cliente cliente = this.modelMapper.map(clienteDTO, Cliente.class);
            this.clienteDAO.save(cliente);
        } catch (Exception e){
            throw new UnsupportedOperationException("Error al guardar el cliente");
        }
    }

    @Override
    public void deleteById(Long id) {

        this.clienteDAO.deleteById(id);
    }


}
