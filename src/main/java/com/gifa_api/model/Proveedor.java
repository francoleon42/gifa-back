package com.gifa_api.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.LinkedHashSet;
import java.util.Set;

@Getter
@Setter
@Entity
@Table(name = "proveedor")
public class Proveedor {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Integer id;

    @Column(name = "nombre", length = 100)
    private String nombre;

    @Column(name = "email", length = 100)
    private String email;

    @OneToMany(mappedBy = "proveedor")
    private Set<Pedido> pedidos = new LinkedHashSet<>();

    @OneToMany(mappedBy = "proveedor")
    private Set<ProveedorDeParte> proveedorDePartes = new LinkedHashSet<>();

}