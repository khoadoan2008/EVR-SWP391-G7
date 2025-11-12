package com.khoadoan.basic.demoswp.controllers;

import com.khoadoan.basic.demoswp.entity.Vehicle;
import com.khoadoan.basic.demoswp.service.VehicleService;
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
    public ResponseEntity<Vehicle> getVehicle(@RequestParam Integer vehicleId) {
        return ResponseEntity.ok(vehicleService.getVehicle(vehicleId));
    }

    @GetMapping("/vehicles")
    public ResponseEntity<List<Vehicle>> findVehicles(
            @RequestParam(required = false) Integer modelId,
            @RequestParam(required = false) BigDecimal minBattery) {
        return ResponseEntity.ok(vehicleService.findVehicles(modelId, minBattery));
    }

    @PostMapping("/vehicles/{id}/report")
    public ResponseEntity<Map<String, Object>> reportVehicleIssue(
            @PathVariable Integer vehicleId,
            @RequestParam Integer userId,
            @RequestParam String issueCategory,
            @RequestParam String priority,
            @RequestParam String description,
            @RequestParam(required = false) MultipartFile[] photos) {
        return ResponseEntity.ok(vehicleService.reportVehicleIssue(vehicleId, userId, issueCategory, priority, description, photos));
    }

    @PutMapping("/vehicles")
    public ResponseEntity<Vehicle> createVehicle(@RequestBody Vehicle vehicle) {
        return ResponseEntity.ok(vehicleService.createVehicle(vehicle));
    }

    @PutMapping("/vehicles/{id}")
    public ResponseEntity<Vehicle> updateVehicle(@PathVariable Integer vehicleId, @RequestBody Vehicle vehicleUpdates) {
        return ResponseEntity.ok(vehicleService.updateVehicle(vehicleId, vehicleUpdates));
    }

    @DeleteMapping("/vehicles/{id}")
    public ResponseEntity<Map<String, Object>> deleteVehicle(@PathVariable Integer vehicleId) {
        return ResponseEntity.ok(vehicleService.deleteVehicle(vehicleId));
    }

}
