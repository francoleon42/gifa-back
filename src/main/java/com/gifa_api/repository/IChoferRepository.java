package com.gifa_api.repository;

import com.gifa_api.model.Chofer;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface IChoferRepository extends JpaRepository<Chofer, Integer> {
    Optional<Chofer> findByUsuario_Id(Integer usuarioId);

    @Query("SELECT c.nombre FROM Chofer c WHERE c.vehiculo.id = :idVehiculo")
    public List<String> obtenerNombreDeChofersDeVehiculo(@Param("idVehiculo") Integer idVehiculo);

    @EntityGraph(attributePaths = {"vehiculo"})
    Optional<Chofer> findByIdWithVehiculo(Integer id);
}
