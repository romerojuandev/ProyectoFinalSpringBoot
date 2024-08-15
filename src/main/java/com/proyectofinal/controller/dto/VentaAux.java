package com.proyectofinal.controller.dto;

import com.proyectofinal.entities.Cliente;
import com.proyectofinal.entities.Producto;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class VentaAux {

    private Cliente cliente;
    private List<Producto> productos;
}
