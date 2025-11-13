package com.khoadoan.basic.demoswp.controllers;

import com.khoadoan.basic.demoswp.entity.Station;
import com.khoadoan.basic.demoswp.service.StationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api")
public class StationController {

    @Autowired
    private StationService stationService;

    @GetMapping("/stations")
    public ResponseEntity<List<Station>> getStations() {
        return ResponseEntity.ok(stationService.getStations());
    }

    // Nearby stations by bounding box around lat/lng with radius (approx using degrees)
    @GetMapping("/stations/nearby")
    public ResponseEntity<List<Station>> getNearby(
            @RequestParam Double lat,
            @RequestParam Double lng,
            @RequestParam(defaultValue = "0.02") Double radiusDeg
    ) {
        Double minLat = lat - radiusDeg;
        Double maxLat = lat + radiusDeg;
        Double minLng = lng - radiusDeg;
        Double maxLng = lng + radiusDeg;
        return ResponseEntity.ok(
                stationService.getNearby(minLat, maxLat, minLng, maxLng)
        );
    }

    // Admin: Station CRUD operations
    @PostMapping("/stations")
    public ResponseEntity<Station> createStation(@RequestBody Station station) {
        return ResponseEntity.ok(stationService.createStation(station));
    }

    @PutMapping("/stations/{id}")
    public ResponseEntity<Station> updateStation(@PathVariable Integer id, @RequestBody Station stationUpdates) {
        return ResponseEntity.ok(stationService.updateStation(id, stationUpdates));
    }

    @DeleteMapping("/stations/{id}")
    public ResponseEntity<Map<String, Object>> deleteStation(@PathVariable Integer id) {
        return ResponseEntity.ok(stationService.deleteStation(id));
    }
}

