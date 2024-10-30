package com.gifa_api.model;

import com.gifa_api.utils.enums.EstadoChofer;
import jakarta.persistence.*;
import lombok.*;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "chofer")
public class Chofer {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @OneToOne(fetch = FetchType.LAZY, cascade = CascadeType.PERSIST)
    @JoinColumn(name = "id_usuario", nullable = false)
    private Usuario usuario;

    @Column(name = "nombre", nullable = false, length = 10)
    private String nombre;

    @Enumerated(EnumType.STRING)
    @Column(name = "estadoChofer", nullable = false)
    private EstadoChofer estadoChofer;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_vehiculo")
    private Vehiculo vehiculo;

    public void cambiarEstadoChofer(EstadoChofer estadoChofer) {
        this.estadoChofer = estadoChofer;
    }
    public void addVehiculo(Vehiculo vehiculo){
        this.vehiculo = vehiculo;
    }
    public void desvincularVehiculo(){
        this.vehiculo = null;
    }
}
