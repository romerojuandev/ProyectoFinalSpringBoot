package com.proyectofinal.controller.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ProductoDTO {

    private Long codigoProducto;
    private String nombre;
    private String marca;
    private Double costo;
    private int cantidadDisponible;
}
