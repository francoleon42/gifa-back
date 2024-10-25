package com.gifa_api.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.gifa_api.utils.enums.EstadoMantenimiento;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;
import java.util.Set;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "mantenimiento")
public class Mantenimiento {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Integer id;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
    @Column(name = "fecha_inicio")
    private LocalDate fechaInicio;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
    @Column(name = "fecha_finalizacion")
    private LocalDate fechaFinalizacion;

    @Column(name = "asunto")
    private String asunto;

    @Enumerated(EnumType.STRING)
    @Column(name = "estado_mantenimiento", nullable = false)
    private EstadoMantenimiento estadoMantenimiento;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "operador_id")
    private Usuario operador;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "vehiculo_id")
    private Vehiculo vehiculo;

    @OneToMany(mappedBy = "mantenimiento",  cascade = CascadeType.ALL)
    private Set<ItemUsadoMantenimiento> itemUsadoMantenimientos;


    public void finalizar() {
        this.estadoMantenimiento = EstadoMantenimiento.FINALIZADO;
    }
    public void addOperador(Usuario usuario) {
        this.operador = usuario;
    }
    public void aprobar(){
        this.estadoMantenimiento = EstadoMantenimiento.APROBADO;
    }

}