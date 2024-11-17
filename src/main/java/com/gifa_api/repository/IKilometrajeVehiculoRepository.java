package com.gifa_api.repository;

import com.gifa_api.model.KilometrajeVehiculo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface IKilometrajeVehiculoRepository extends JpaRepository<KilometrajeVehiculo, Integer> {
    @Query("SELECT k from  KilometrajeVehiculo  k where k.vehiculo.id = :idVehiculo")
    List<KilometrajeVehiculo> findByIdVehiculo(@Param("idVehiculo") Integer idVehiculo);
}