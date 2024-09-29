package com.gifa_api.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
@Entity
@Table(name = "kilometraje_vehiculo")
public class KilometrajeVehiculo {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Integer id;

    @Column(name = "kilometros_recorridos")
    private Float kilometrosRecorridos;

    @Column(name = "kilometro_al_fin_trayecto")
    private Float kilometroAlFinTrayecto;

    @Column(name = "kilometro_inicio_trayecto")
    private Float kilometroInicioTrayecto;

    @Column(name = "fecha_inicio")
    private LocalDate fechaInicio;

    @Column(name = "fecha_fin")
    private LocalDate fechaFin;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "vehiculo_id")
    private Vehiculo vehiculo;

}