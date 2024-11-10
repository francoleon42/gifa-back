package com.gifa_api.repository;

import com.gifa_api.model.Vehiculo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface IVehiculoRepository extends JpaRepository<Vehiculo, Integer> {
//    @Query("select v from Vehiculo v where v.dispositivo.unicoId=:unicoId")
//    public Optional<Vehiculo> findVehiculosDeDispositivo(@Param("unicoId") String unicoId);
    @Query("select v from Vehiculo v where v.patente=:patente")
    public Optional<Vehiculo> findByPatente(@Param("patente") String patente);



}
