package com.khoadoan.basic.demoswp.service;

import com.khoadoan.basic.demoswp.entity.Vehicle;
import org.springframework.web.multipart.MultipartFile;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

public interface VehicleService {
    public List<Vehicle> getAvailableVehicles(Integer stationId);
    public Vehicle getVehicle(Integer vehicleId);
    public List<Vehicle> findVehicles(Integer modelId, BigDecimal minBattery);
    public Map<String, Object> reportVehicleIssue(Integer vehicleId, Integer userId, String issueCategory, String priority, String description, MultipartFile[] photos) ;
    public Vehicle createVehicle(Vehicle vehicle);
    public Vehicle updateVehicle(Integer vehicleId, Vehicle vehicleUpdates);
    public Map<String, Object> deleteVehicle(Integer vehicleId);
}
