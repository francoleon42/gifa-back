package com.gifa_api.repository;

import com.gifa_api.model.Dispositivo;
import com.gifa_api.model.Vehiculo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface IDispositivoRepository extends JpaRepository<Dispositivo, Integer> {
    @Query("SELECT d FROM Dispositivo d WHERE d.unicoId = :id")
    public Optional<Dispositivo> findByUnicoId(@Param("id") String id);

    @Query("select d.vehiculo from Dispositivo d where d.unicoId=:unicoId")
    public Optional<Vehiculo> findVehiculosDeDispositivo(@Param("unicoId") String unicoId);
}
