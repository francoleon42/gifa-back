package com.gifa_api.repository;

import com.gifa_api.model.Chofer;
import com.gifa_api.model.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
@Repository
public interface ChoferRepository extends JpaRepository<Chofer, Integer> {

    Optional<Chofer> findById(Integer idChofer);
}
