package com.gifa_api.repository;

import com.gifa_api.model.Usuario;
import com.gifa_api.model.Vehiculo;
import org.springframework.data.jpa.repository.JpaRepository;

public interface VehiculoRepository extends JpaRepository<Vehiculo, Integer> {

}
