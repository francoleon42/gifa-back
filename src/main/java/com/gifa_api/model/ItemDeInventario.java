package com.gifa_api.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.LinkedHashSet;
import java.util.Set;

@Getter
@Setter
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

    @OneToMany(mappedBy = "repuestoUtilizado")
    private Set<Mantenimiento> mantenimientos = new LinkedHashSet<>();

    @OneToMany(mappedBy = "itemDeInventario")
    private Set<ParteDeVehiculo> parteDeVehiculos = new LinkedHashSet<>();

    @OneToMany(mappedBy = "item")
    private Set<Pedido> pedidos = new LinkedHashSet<>();

    @OneToMany(mappedBy = "itemDeInventario")
    private Set<ProveedorDeParte> proveedorDePartes = new LinkedHashSet<>();

}