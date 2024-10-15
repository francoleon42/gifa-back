package com.gifa_api.model;

import jakarta.persistence.*;
import lombok.*;

import java.time.Instant;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "posicion")
public class Posicion {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Integer id;

    @Column(name = "latitud")
    private Float latitud;

    @Column(name = "longitud")
    private Float longitud;

    @Column(name = "fecha_hora")
    private Instant fechaHora;

    @Column(name = "distancia_desde_ultima_posicion")
    private Float distanciaDesdeUltimaPosicion;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "dispositivo_id")
    private Dispositivo dispositivo;

}