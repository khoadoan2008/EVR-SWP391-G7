package com.group7.evr.controllers;

import com.group7.evr.entity.Vehicle;
import com.group7.evr.enums.VehicleStatus;
import com.group7.evr.service.VehicleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import com.group7.evr.repository.VehicleRepository;
import org.springframework.web.multipart.MultipartFile;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api")
public class VehicleController {
    @Autowired
    private VehicleRepository vehicleRepository;
    @Autowired
    private VehicleService vehicleService;

    @GetMapping("/vehicles/available")
    public ResponseEntity<List<Vehicle>> getAvailableVehicles(@RequestParam Integer stationId) {
        return ResponseEntity.ok(vehicleRepository.findByStationStationIdAndStatus(stationId, VehicleStatus.AVAILABLE));
    }

    @GetMapping("/vehicles/{id}")
    public ResponseEntity<Vehicle> getVehicle(@PathVariable Integer id) {
        return ResponseEntity.of(vehicleRepository.findById(id));
    }

    @GetMapping("/vehicles")
    public ResponseEntity<List<Vehicle>> findVehicles(
            @RequestParam(required = false) Integer modelId,
            @RequestParam(required = false) BigDecimal minBattery
    ) {
        if (modelId != null) {
            return ResponseEntity.ok(vehicleRepository.findByModelModelId(modelId));
        }
        if (minBattery != null) {
            return ResponseEntity.ok(vehicleRepository.findByBatteryLevelGreaterThanEqual(minBattery));
        }
        return ResponseEntity.ok(vehicleRepository.findAll());
    }

    // Vehicle issue reporting
    @PostMapping("/vehicles/{id}/report")
    public ResponseEntity<Map<String, Object>> reportVehicleIssue(
            @PathVariable Integer id,
            @RequestParam Integer userId,
            @RequestParam String issueCategory,
            @RequestParam String priority,
            @RequestParam String description,
            @RequestParam(required = false) MultipartFile[] photos) {
        return ResponseEntity.ok(vehicleService.reportVehicleIssue(id, userId, issueCategory, priority, description, photos));
    }

    // Admin: Vehicle CRUD operations
    @PostMapping("/vehicles")
    public ResponseEntity<Vehicle> createVehicle(@RequestBody Vehicle vehicle) {
        return ResponseEntity.ok(vehicleService.createVehicle(vehicle));
    }

    @PutMapping("/vehicles/{id}")
    public ResponseEntity<Vehicle> updateVehicle(@PathVariable Integer id, @RequestBody Vehicle vehicleUpdates) {
        return ResponseEntity.ok(vehicleService.updateVehicle(id, vehicleUpdates));
    }

    @DeleteMapping("/vehicles/{id}")
    public ResponseEntity<Map<String, Object>> deleteVehicle(@PathVariable Integer id) {
        return ResponseEntity.ok(vehicleService.deleteVehicle(id));
    }
}
