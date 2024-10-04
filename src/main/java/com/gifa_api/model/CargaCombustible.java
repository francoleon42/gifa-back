package com.gifa_api.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "carga_combustible")
public class CargaCombustible {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Integer id;

    @Column(name = "cantidad_litros")
    private Integer cantidadLitros;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
    @Column(name = "fecha_hora")
    private LocalDate fechaHora;

    @Column(name = "precio_por_litro")
    private Float precioPorLitro;

    @Column(name = "costo_total")
    private Float costoTotal;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "vehiculo_id")
    private com.gifa_api.model.Vehiculo vehiculo;

}