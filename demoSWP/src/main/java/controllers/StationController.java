package controllers;

import entity.Station;
import entity.Vehicle;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import repository.StationRepository;
import repository.VehicleRepository;

import java.util.List;

@RestController
@RequestMapping("/api")
public class StationController {
    @Autowired
    private StationRepository stationRepository;

    @GetMapping("/stations")
    public ResponseEntity<List<Station>> getStations() {
        return ResponseEntity.ok(stationRepository.findAll());
    }
}

