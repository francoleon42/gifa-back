package com.gifa_api.repository;

import com.gifa_api.model.CargaCombustible;
import com.gifa_api.model.Mantenimiento;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ICargaCombustibleRepository extends JpaRepository<CargaCombustible, Integer> {
}
