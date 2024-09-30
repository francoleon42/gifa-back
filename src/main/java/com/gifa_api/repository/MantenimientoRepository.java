package com.gifa_api.repository;

import com.gifa_api.model.Mantenimiento;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface MantenimientoRepository extends JpaRepository<Mantenimiento, Integer> {
    @Query("SELECT m FROM Mantenimiento m WHERE m.vehiculo.id = :vehiculoId")
    List<Mantenimiento> findByVehiculoId(@Param("vehiculoId") Integer vehiculoId);
}
