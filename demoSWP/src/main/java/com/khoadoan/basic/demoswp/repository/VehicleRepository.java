package com.khoadoan.basic.demoswp.repository;

import com.khoadoan.basic.demoswp.entity.Vehicle;
import org.springframework.data.jpa.repository.JpaRepository;

import java.math.BigDecimal;
import java.util.List;

public interface VehicleRepository extends JpaRepository<Vehicle,Integer> {
    List<Vehicle> findByStationStationIdAndStatus(Integer stationId, String status);

    List<Vehicle> findByStationStationId(Integer stationId);

    Vehicle getVehicleById(Integer vehicleId);

    List<Vehicle> findByModelModelId(Integer modelId);

    List<Vehicle> findByBatteryLevelGreaterThanEqual(BigDecimal minBattery);

    // NEW: Combined filter method
    List<Vehicle> findByModelModelIdAndBatteryLevelGreaterThanEqual(Integer modelId, BigDecimal minBattery);
}
