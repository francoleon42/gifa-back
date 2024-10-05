package com.gifa_api.repository;

import com.gifa_api.model.Pedido;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface IPedidoRepository extends JpaRepository<Pedido, Integer> {
    @Query("SELECT p FROM Pedido p WHERE p.item.id = :itemId")
    boolean findByItemId(@Param("itemId") Integer itemId);

}
