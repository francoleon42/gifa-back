package com.gifa_api.model;

import com.gifa_api.enums.EstadoDeHabilitacion;
import com.gifa_api.enums.EstadoVehiculo;
import jakarta.persistence.*;
import lombok.*;

import java.util.LinkedHashSet;
import java.util.Set;

@Builder
@Getter
@Setter
@Entity
@Table(name = "vehiculo")
@NoArgsConstructor
@AllArgsConstructor
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

    @Column(name = "litros_de_tanque")
    private Integer litrosDeTanque;

    @Column(name = "modelo", length = 50)
    private String modelo;

    @Enumerated(EnumType.STRING)
    @Column(name = "estado_vehiculo", nullable = false)
    private EstadoVehiculo estadoVehiculo;

    @Enumerated(EnumType.STRING)
    @Column(name = "habilitado", nullable = false)
    private EstadoDeHabilitacion estadoDeHabilitacion;

    @Column(name = "qr")
    private byte[] qr;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "chofer_id")
    private Chofer chofer;

    @OneToMany(mappedBy = "vehiculo")
    private Set<CargaCombustible> cargaCombustibles = new LinkedHashSet<>();

    @OneToMany(mappedBy = "vehiculo")
    private Set<GpsData> gpsData = new LinkedHashSet<>();

    @OneToMany(mappedBy = "vehiculo")
    private Set<KilometrajeVehiculo> kilometrajeVehiculos = new LinkedHashSet<>();

    @OneToMany(mappedBy = "vehiculo")
    private Set<Mantenimiento> mantenimientos = new LinkedHashSet<>();

    @OneToMany(mappedBy = "vehiculo")
    private Set<ParteDeVehiculo> parteDeVehiculos = new LinkedHashSet<>();

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "tarjeta_id")
    private Tarjeta tarjeta;

}