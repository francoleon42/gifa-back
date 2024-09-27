package com.gifa_api.model;

import com.gifa_api.enums.EstadoDeHabilitacion;
import com.gifa_api.enums.EstadoVehiculo;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.antlr.v4.runtime.misc.NotNull;

import java.util.LinkedHashSet;
import java.util.Set;

@Getter
@Setter
@Entity
public class Vehiculo {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Integer id;

    @Column(name = "patente", nullable = false, length = 10)
    private String patente;

    @Column(name = "chasis", nullable = false, length = 50)
    private String chasis;

    @Column(name = "antiguedad")
    private Integer antiguedad;

    @Column(name = "kilometraje")
    private Integer kilometraje;

    @Column(name = "litrosDeTanque")
    private Integer litrosDeTanque;

    @Column(name = "modelo", length = 50)
    private String modelo;

//    @Lob
//    @Column(name = "estadoVehiculo", nullable = false)
//    private String estadoVehiculo;
    @NotNull
    @Enumerated(EnumType.STRING)
    @Column(name = "estadoVehiculo", nullable = false)
    private EstadoVehiculo type;

    @NotNull
    @Enumerated(EnumType.STRING)
    @Column(name = "habilitado", nullable = false)
    private EstadoDeHabilitacion habilitado;

    @Column(name = "qr")
    private byte[] qr;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "chofer_id")
    private Chofer chofer;

    @OneToMany(mappedBy = "vehiculo")
    private Set<CargaCombustible> cargaCombustibles = new LinkedHashSet<>();

    @OneToMany(mappedBy = "vehiculo")
    private Set<Mantenimiento> mantenimientos = new LinkedHashSet<>();

    @OneToMany(mappedBy = "vehiculo")
    private Set<ParteDeVehiculo> parteDeVehiculos = new LinkedHashSet<>();

    @OneToMany(mappedBy = "vehiculo")
    private Set<Tarjeta> tarjetas = new LinkedHashSet<>();

}