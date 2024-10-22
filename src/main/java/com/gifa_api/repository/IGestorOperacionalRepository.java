package com.gifa_api.repository;


import com.gifa_api.model.GestorOperacional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface IGestorOperacionalRepository extends JpaRepository<GestorOperacional, Integer> {
}
