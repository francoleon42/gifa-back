package com.gifa_api.repository;

import com.gifa_api.model.Mantenimiento;
import com.gifa_api.utils.enums.EstadoMantenimiento;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface IMantenimientoRepository extends JpaRepository<Mantenimiento, Integer> {
    @Query("SELECT m FROM Mantenimiento m WHERE m.vehiculo.id = :vehiculoId")
    List<Mantenimiento> findByVehiculoId(@Param("vehiculoId") Integer vehiculoId);

    List<Mantenimiento> findAllByEstadoMantenimiento(EstadoMantenimiento estadoMantenimiento);

    @EntityGraph(attributePaths = {"mantenimiento", "mantenimiento.vehiculo"})
    Optional<Mantenimiento> findById(Integer id);
}
