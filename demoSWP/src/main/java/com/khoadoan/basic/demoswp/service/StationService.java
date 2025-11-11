package com.khoadoan.basic.demoswp.service;

import com.khoadoan.basic.demoswp.entity.Station;
import org.springframework.http.ResponseEntity;

import java.util.List;
import java.util.Map;

public interface StationService {
    public List<Station> getStations();
    public List<Station> getNearby(double minLat, double maxLat, double minLng, double maxLng);
    public Station createStation(Station station);
    public Station updateStation(Integer stationId, Station stationUpdates);
    public Map<String, Object> deleteStation(Integer stationId);

}
