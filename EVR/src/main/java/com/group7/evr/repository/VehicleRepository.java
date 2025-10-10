package com.group7.evr.repository;

import com.group7.evr.entity.Vehicle;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface VehicleRepository extends JpaRepository<Vehicle,Integer> {
    List<Vehicle> findByStationStationIdAndStatus(Integer stationId, String status);
}
