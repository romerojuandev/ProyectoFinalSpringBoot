package com.proyectofinal.entities;

import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;

@Getter
@Setter
@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Producto {

    @Id
    @Column(name = "codigo_producto")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long codigoProducto;
    private String nombre;
    private String marca;
    private double costo;

    @Column(name = "cantidad_solicitada")
    private int cantidadSolicitada;

    @Column(name = "cantidad_disponible")
    private int cantidadDisponible;
}
