package com.gifa_api.repository;

import com.gifa_api.model.Dispositivo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface IDispositivoRepository extends JpaRepository<Dispositivo, Integer> {
    @Query("Select d from Dispositivo d where d.unicoId =:id")
    public Optional<Dispositivo> findByUnicoId(@Param("id") String id);
}
