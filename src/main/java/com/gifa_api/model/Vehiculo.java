package com.gifa_api.model;

import com.gifa_api.utils.enums.EstadoDeHabilitacion;
import com.gifa_api.utils.enums.EstadoVehiculo;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;
import java.util.LinkedHashSet;
import java.util.Set;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "vehiculo")
public class Vehiculo {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Integer id;

    @Column(name = "patente", nullable = false, length = 10)
    private String patente;

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

    @Column(name = "fecha_vencimiento")
    private LocalDate fechaVencimiento;

    @OneToMany(mappedBy = "vehiculo")
    private Set<CargaCombustible> cargasCombustible ;

    @OneToMany(mappedBy = "vehiculo", cascade = CascadeType.ALL)
    private Set<CargaCombustible> cargasCombustibles;

    @OneToMany(mappedBy = "vehiculo", cascade = CascadeType.ALL)
    private Set<GpsData> gpsDataSet;

    @OneToMany(mappedBy = "vehiculo", cascade = CascadeType.ALL)
    private Set<KilometrajeVehiculo> kilometrajeVehiculos;

    @OneToMany(mappedBy = "vehiculo", cascade = CascadeType.ALL)
    private Set<Mantenimiento> mantenimientos;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "tarjeta_id")
    private Tarjeta tarjeta;


    public void inhabilitar(){
        this.estadoDeHabilitacion= EstadoDeHabilitacion.INHABILITADO;
    }
    public void habilitar(){
        this.estadoDeHabilitacion= EstadoDeHabilitacion.HABILITADO;
    }

}