package com.proyectofinal.controller.dto;

import com.proyectofinal.entities.Cliente;
import com.proyectofinal.entities.Producto;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;


import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class VentaDTO {

    private Long codigo_venta;
    private LocalDate fecha_venta;
    private Double total;
    private Cliente cliente;
    private List<Producto> listaProductos = new ArrayList<>();
}
