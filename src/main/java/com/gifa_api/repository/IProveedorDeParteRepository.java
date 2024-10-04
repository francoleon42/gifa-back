package com.gifa_api.repository;

import com.gifa_api.model.ProveedorDeItem;
import org.springframework.data.jpa.repository.JpaRepository;

public interface IProveedorDeParteRepository extends JpaRepository<ProveedorDeItem, Integer> {
}
