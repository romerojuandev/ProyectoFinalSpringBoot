package com.proyectofinal.entities;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Venta {

    @Id
    @Column(name = "codigo_venta")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long codigo_venta;

    @Column(name = "fecha_venta")
    private LocalDate fecha_venta;
    private Double total;

    @OneToOne
    @JoinColumn(name = "id_cliente", referencedColumnName = "id_cliente")
    private Cliente cliente;

    @OneToMany
    @JoinTable(name = "ventas_lista_productos", joinColumns = @JoinColumn(name = "id_venta"), inverseJoinColumns = @JoinColumn(name = "id_producto"))
    private List<Producto> listaProductos = new ArrayList<Producto>();

}
