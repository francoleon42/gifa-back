package com.gifa_api.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.LinkedHashSet;
import java.util.Set;

@Getter
@Setter
@Entity
public class Chofer {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Integer id;

    @Column(name = "nombre", nullable = false, length = 100)
    private String nombre;

    @Column(name = "licencia", nullable = false, length = 50)
    private String licencia;

    @Column(name = "telefono", length = 20)
    private String telefono;

    @OneToMany(mappedBy = "chofer")
    private Set<Vehiculo> vehiculos = new LinkedHashSet<>();

}