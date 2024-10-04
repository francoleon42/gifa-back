package com.gifa_api.repository;

import com.gifa_api.model.GpsData;
import org.springframework.data.jpa.repository.JpaRepository;

public interface IGpsDataRepository extends JpaRepository<GpsData, Integer> {
}
