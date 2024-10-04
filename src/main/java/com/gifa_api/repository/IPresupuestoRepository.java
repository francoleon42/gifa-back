package com.gifa_api.repository;

import com.gifa_api.model.Presupuesto;
import org.springframework.data.jpa.repository.JpaRepository;

public interface IPresupuestoRepository extends JpaRepository<Presupuesto, Integer> {
}
