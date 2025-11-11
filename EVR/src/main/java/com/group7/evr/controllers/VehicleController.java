package com.group7.evr.controllers;

import com.group7.evr.entity.Vehicle;
import com.group7.evr.service.VehicleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api")
public class VehicleController {
    @Autowired
    private VehicleService vehicleService;

    @GetMapping("/vehicles/available")
    public ResponseEntity<List<Vehicle>> getAvailableVehicles(@RequestParam Integer stationId) {
        return ResponseEntity.ok(vehicleService.getAvailableVehicles(stationId));
    }

    @GetMapping("/vehicles/{id}")
    public ResponseEntity<Vehicle> getVehicle(@PathVariable Integer id) {
        return ResponseEntity.ok(vehicleService.getVehicle(id));
    }

    @GetMapping("/vehicles")
    public ResponseEntity<List<Vehicle>> findVehicles(
            @RequestParam(required = false) Integer modelId,
            @RequestParam(required = false) BigDecimal minBattery) {
        return ResponseEntity.ok(vehicleService.findVehicles(modelId, minBattery));
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
