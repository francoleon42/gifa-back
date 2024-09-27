package com.gifa_api.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.LinkedHashSet;
import java.util.Set;

@Getter
@Setter
@Entity
public class Ubicacion {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Integer id;

    @Column(name = "field", length = 100)
    private String field;

    @OneToMany(mappedBy = "ubicacion")
    private Set<Mantenimiento> mantenimientos = new LinkedHashSet<>();

}