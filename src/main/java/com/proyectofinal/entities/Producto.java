package com.proyectofinal.entities;

import jakarta.persistence.*;
import lombok.*;

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
    private Double costo;

    @Transient
    private int cantidadSolicitada;

    @Column(name = "cantidad_disponible")
    private int cantidadDisponible;
}
