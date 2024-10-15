package com.gifa_api.repository;


import com.gifa_api.model.Posicion;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface IPosicionRepository extends JpaRepository<Posicion, Integer> {
    @Query("SELECT p FROM Posicion  p WHERE p.dispositivo.unicoId=:unicoId")
    public List<Posicion> findByUnicoId(@Param("unicoId") String unicoId);
}
