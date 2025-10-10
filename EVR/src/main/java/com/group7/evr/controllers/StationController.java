package com.group7.evr.controllers;

import com.group7.evr.entity.Station;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.group7.evr.repository.StationRepository;

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

