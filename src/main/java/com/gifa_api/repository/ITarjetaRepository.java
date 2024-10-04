package com.gifa_api.repository;

import com.gifa_api.model.Tarjeta;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ITarjetaRepository extends JpaRepository<Tarjeta, Integer> {
}
