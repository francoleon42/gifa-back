package com.gifa_api.repository;

import com.gifa_api.model.ItemDeInventario;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;

public interface ItemDeInventarioRepository extends JpaRepository<ItemDeInventario, Integer> {
    Optional<ItemDeInventario> findById(int id);
}

