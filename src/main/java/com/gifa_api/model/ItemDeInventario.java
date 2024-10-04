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

    @JsonManagedReference
    @OneToMany(mappedBy = "repuestoUtilizado")
    private Set<Mantenimiento> mantenimientos = new LinkedHashSet<>();

    @OneToMany(mappedBy = "item")
    private Set<Pedido> pedidos = new LinkedHashSet<>();

    @OneToMany
    @JoinColumn(name = "proveedor_de_item_id")
    private Set<ProveedorDeItem> proveedorDeItems;

    public void desminuirStock() {
        this.stock -= 1;
    }

}