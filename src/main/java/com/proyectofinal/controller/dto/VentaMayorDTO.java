package com.proyectofinal.controller.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class VentaMayorDTO {

    private Long codigoVenta;
    private double total;
    private int cantidadProductos;
    private String nombreCliente;
    private String apellidoCliente;
}
