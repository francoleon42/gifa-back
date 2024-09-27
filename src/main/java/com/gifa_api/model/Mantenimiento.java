package com.gifa_api.model;

import com.gifa_api.enums.EstadoMantenimiento;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.antlr.v4.runtime.misc.NotNull;

import java.time.LocalDate;

@Getter
@Setter
@Entity
public class Mantenimiento {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Integer id;

    @Column(name = "fechaInicio")
    private LocalDate fechaInicio;

    @Column(name = "fechaFinalizacion")
    private LocalDate fechaFinalizacion;

    @Column(name = "asunto")
    private String asunto;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "repuestoUtilizado")
    private Parte repuestoUtilizado;

    @NotNull
    @Enumerated(EnumType.STRING)
    @Column(name = "estadoMantenimiento", nullable = false)
    private EstadoMantenimiento estadoMantenimiento;


    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "vehiculo_id")
    private Vehiculo vehiculo;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "ubicacion_id")
    private Ubicacion ubicacion;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "operador_id")
    private Usuario operador;

}