package com.gifa_api.repository;

import com.gifa_api.model.Chofer;
import com.gifa_api.model.Vehiculo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface IChoferRepository extends JpaRepository<Chofer, Integer> {
    Optional<Chofer> findByUsuario_Id(Integer usuarioId);

    @Query("SELECT c.nombre FROM Chofer c WHERE c.vehiculo.id = :idVehiculo")
    public List<String> obtenerNombreDeChofersDeVehiculo(@Param("idVehiculo") Integer idVehiculo);

    @Query("SELECT c FROM Chofer c JOIN FETCH c.vehiculo WHERE c.id = :id")
    Optional<Chofer> findByIdWithVehiculo(@Param("id") Integer id);


    @Query("select c.vehiculo from Chofer c where c.usuario.id =:idUsuario")
    public Vehiculo findVehiculoByChofer(@Param("idUsuario") Integer idUsuario);
}
