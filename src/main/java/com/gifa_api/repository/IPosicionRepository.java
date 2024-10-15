package com.gifa_api.repository;


import com.gifa_api.model.Posicion;
import org.springframework.data.jpa.repository.JpaRepository;

public interface IPosicionRepository extends JpaRepository<Posicion, Integer> {
}
