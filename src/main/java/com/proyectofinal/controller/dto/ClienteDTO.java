package com.proyectofinal.controller.dto;

import com.proyectofinal.entities.Venta;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ClienteDTO {

    private Long idCliente;
    private String nombre;
    private String apellido;
    private String dni;
    private List<Venta> ventaList = new ArrayList<>();
}
