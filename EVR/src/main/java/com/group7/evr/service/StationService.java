package com.group7.evr.service;

import com.group7.evr.entity.Station;

import java.util.Map;

public interface StationService {

    public Station createStation(Station station);

    public Station updateStation(Integer stationId, Station stationUpdates);

    public Map<String, Object> deleteStation(Integer stationId);
}