package com.gifa_api.repository;

import com.gifa_api.model.CargaCombustible;
import com.gifa_api.model.Mantenimiento;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.OffsetDateTime;
import java.util.List;

public interface ICargaCombustibleRepository extends JpaRepository<CargaCombustible, Integer> {

//    @Query("SELECT c FROM CargaCombustible c WHERE c.tarjeta.numero = :numeroTarjeta AND c.fechaHora > :fecha")
//    public List<CargaCombustible> findByNumeroTarjetaAndFechaAfter(@Param("numeroTarjeta") Integer numeroTarjeta, @Param("fecha") LocalDate fecha);

    @Query("SELECT c FROM CargaCombustible c WHERE c.tarjeta.numero = :numeroTarjeta AND c.fechaHora > :fechaInicio AND c.fechaHora < :fechaFin")
    public List<CargaCombustible> findByNumeroTarjetaAndFechaBetween(
            @Param("numeroTarjeta") Integer numeroTarjeta,
            @Param("fechaInicio") OffsetDateTime fechaInicio,
            @Param("fechaFin") OffsetDateTime fechaFin
    );

}
