package com.proyectofinal.entities;


import jakarta.persistence.*;
import lombok.*;

@Getter
@Setter
@Entity
@Builder
@Table(name = "detalle_venta")
@NoArgsConstructor
@AllArgsConstructor
public class DetalleVenta {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name = "id_venta")
    private Long idVenta;
    @Column(name = "id_producto")
    private Long idProducto;
    private int cantidad;
}
