package com.gifa_api.repository;


import com.gifa_api.model.Posicion;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.OffsetDateTime;
import java.util.List;
import java.util.Optional;

public interface IPosicionRepository extends JpaRepository<Posicion, Integer> {
    @Query("SELECT p FROM Posicion  p WHERE p.dispositivo.unicoId=:unicoId")
    public List<Posicion> findByUnicoId(@Param("unicoId") String unicoId);

    @Query("SELECT p FROM Posicion p WHERE p.dispositivo.unicoId=:unicoId AND p.fechaHora > :fecha")
    List<Posicion> findByUnicoIdAndDespuesFecha(@Param("unicoId") String unicoId, @Param("fecha") OffsetDateTime fecha);

    @Query("SELECT p FROM Posicion p WHERE p.dispositivo.unicoId=:unicoId AND p.fechaHora = :fecha")
    Optional<Posicion> obtenerPosicionDeDispositivoPorFecha(@Param("unicoId") String unicoId, @Param("fecha") OffsetDateTime fecha);
}
