package com.proyectofinal.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
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

    @ManyToOne
    @JoinTable(
            name = "ventas_por_cliente",
            joinColumns = @JoinColumn(name = "id_venta"),
            inverseJoinColumns = @JoinColumn(name = "id_cliente")
    )
    private Cliente cliente;

//    @ManyToMany(cascade = CascadeType.ALL)
//    @JoinTable(
//            name = "venta_producto",
//            joinColumns = @JoinColumn(name = "id_venta"),
//            inverseJoinColumns = @JoinColumn(name = "id_producto_venta")
//    )
    @OneToMany(mappedBy = "venta")
    @JsonIgnore
    private List<VentaProducto> listaProductos;

    public Venta(Cliente cliente, List<VentaProducto> ventaProductos, Double total){

        this.cliente = cliente;
        this.listaProductos = ventaProductos;
        this.total = total;
    }
}
