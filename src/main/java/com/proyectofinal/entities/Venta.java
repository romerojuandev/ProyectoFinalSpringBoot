package com.proyectofinal.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;
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
    private double total;

    @ManyToOne
    @JoinColumn(name = "id_cliente")
    private Cliente cliente;

    @OneToMany
    @JsonIgnore
    @JoinTable(
            name = "lista_productos",
            joinColumns = @JoinColumn(name = "id_venta"),
            inverseJoinColumns = @JoinColumn(unique = false, name = "id_producto")
    )
    private List<Producto> listaProductos = new ArrayList<>();

}
