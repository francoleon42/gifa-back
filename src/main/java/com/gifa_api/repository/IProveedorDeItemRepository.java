package com.gifa_api.repository;

import com.gifa_api.model.ProveedorDeItem;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface IProveedorDeItemRepository extends JpaRepository<ProveedorDeItem, Integer> {
    //esto creo que esta mal
    @Query("SELECT p FROM ProveedorDeItem p WHERE p.itemDeInventario.id = :itemId ORDER BY p.precio ASC")
    ProveedorDeItem findProveedorMasEconomicoByItemId(@Param("itemId") Integer itemId);
}
