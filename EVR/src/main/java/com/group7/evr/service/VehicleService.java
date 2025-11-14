package com.group7.evr.service;

import com.group7.evr.entity.Vehicle;
import org.springframework.web.multipart.MultipartFile;

import java.util.Map;

public interface VehicleService {

    public Map<String, Object> reportVehicleIssue(Integer vehicleId, Integer userId, String issueCategory,
                                                  String priority, String description, MultipartFile[] photos);

    public Vehicle createVehicle(Vehicle vehicle);

    public Vehicle updateVehicle(Integer vehicleId, Vehicle vehicleUpdates);

    public Map<String, Object> deleteVehicle(Integer vehicleId);
}