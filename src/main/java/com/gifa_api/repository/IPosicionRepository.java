package com.gifa_api.repository;


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

    @Query("SELECT p FROM Posicion p WHERE p.dispositivo.unicoId=:unicoId AND p.latitude = :latitude AND p.longitude = :longitude")
    Optional<Posicion> obtenerPosicionDeDispositivoPorUbicacion(@Param("unicoId") String unicoId, @Param("latitude") double latitude, @Param("longitude") double longitude);

    @Query("SELECT p FROM Posicion p WHERE p.dispositivo.unicoId = :unicoId ORDER BY p.fechaHora DESC")
    List<Posicion> obtenerUltimaPosicion(@Param("unicoId") String unicoId);

}
