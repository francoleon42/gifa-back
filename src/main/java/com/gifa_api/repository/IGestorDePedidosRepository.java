package com.gifa_api.repository;

import com.gifa_api.model.GestorDePedidos;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface IGestorDePedidosRepository extends JpaRepository<GestorDePedidos, Integer> {
}
