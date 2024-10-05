package com.gifa_api.repository;

import com.gifa_api.model.Pedido;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface IPedidoRepository extends JpaRepository<Pedido, Integer> {
    boolean existsByItemId(Integer itemId);

}
