package com.gifa_api.model;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.*;

import java.util.LinkedHashSet;
import java.util.Set;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "item_de_inventario")
public class ItemDeInventario {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Integer id;

    @Column(name = "nombre", nullable = false, length = 100)
    private String nombre;

    @Column(name = "umbral", nullable = false)
    private Integer umbral;

    @Column(name = "stock")
    private Integer stock;

    @Column(name = "cant_compra_automatica")
    private Integer cantCompraAutomatica;

    @OneToMany(mappedBy = "item")
    private Set<Pedido> pedidos = new LinkedHashSet<>();

    @OneToMany(mappedBy = "itemDeInventario", cascade = CascadeType.ALL)
    private Set<ProveedorDeItem> proveedorDeItems;

    @OneToMany(mappedBy = "itemDeInventario", cascade = CascadeType.ALL)
    private Set<ItemUsadoMantenimiento> itemUsadoMantenimientos;



    public void desminuirStock(int cantidad) {
        this.stock -= cantidad;
    }

}