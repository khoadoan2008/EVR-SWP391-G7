package com.group7.evr.controllers;

import com.group7.evr.entity.Vehicle;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import com.group7.evr.repository.VehicleRepository;

import java.util.List;

@RestController
@RequestMapping("/api")
public class VehicleController {
    @Autowired
    private VehicleRepository vehicleRepository;

    @GetMapping("/vehicles/available")
    public ResponseEntity<List<Vehicle>> getAvailableVehicles(@RequestParam Integer stationId) {
        return ResponseEntity.ok(vehicleRepository.findByStationStationIdAndStatus(stationId, "Available"));
    }
}
