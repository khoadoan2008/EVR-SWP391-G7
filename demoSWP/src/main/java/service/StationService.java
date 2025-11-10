package service;

import com.group7.evr.entity.Station;
import com.group7.evr.entity.Vehicle;
import com.group7.evr.repository.StationRepository;
import com.group7.evr.repository.VehicleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class StationService {
    @Autowired
    private StationRepository stationRepository;
    @Autowired
    private VehicleRepository vehicleRepository;
    @Autowired
    private UserService userService;

    public Station createStation(Station station) {
        // Validate required fields
        if (station.getName() == null || station.getName().trim().isEmpty()) {
            throw new RuntimeException("Station name is required");
        }
        if (station.getAddress() == null || station.getAddress().trim().isEmpty()) {
            throw new RuntimeException("Station address is required");
        }
        
        // Set default values
        if (station.getTotalSlots() == null) {
            station.setTotalSlots(10); // Default capacity
        }
        if (station.getAvailableSlots() == null) {
            station.setAvailableSlots(station.getTotalSlots());
        }
        
        Station savedStation = stationRepository.save(station);
        userService.logAudit(null, "Created station " + savedStation.getStationId());
        return savedStation;
    }

    public Station updateStation(Integer stationId, Station stationUpdates) {
        Station existingStation = stationRepository.findById(stationId)
                .orElseThrow(() -> new RuntimeException("Station not found"));
        
        // Update allowed fields
        if (stationUpdates.getName() != null) {
            existingStation.setName(stationUpdates.getName());
        }
        if (stationUpdates.getAddress() != null) {
            existingStation.setAddress(stationUpdates.getAddress());
        }
        if (stationUpdates.getContactNumber() != null) {
            existingStation.setContactNumber(stationUpdates.getContactNumber());
        }
        if (stationUpdates.getOperatingHours() != null) {
            existingStation.setOperatingHours(stationUpdates.getOperatingHours());
        }
        if (stationUpdates.getTotalSlots() != null) {
            existingStation.setTotalSlots(stationUpdates.getTotalSlots());
        }
        if (stationUpdates.getAvailableSlots() != null) {
            existingStation.setAvailableSlots(stationUpdates.getAvailableSlots());
        }
        if (stationUpdates.getLatitude() != null) {
            existingStation.setLatitude(stationUpdates.getLatitude());
        }
        if (stationUpdates.getLongitude() != null) {
            existingStation.setLongitude(stationUpdates.getLongitude());
        }
        
        Station updatedStation = stationRepository.save(existingStation);
        userService.logAudit(null, "Updated station " + stationId);
        return updatedStation;
    }

    public Map<String, Object> deleteStation(Integer stationId) {
        Station station = stationRepository.findById(stationId)
                .orElseThrow(() -> new RuntimeException("Station not found"));
        
        // Check for active vehicles
        List<Vehicle> stationVehicles = vehicleRepository.findByStationStationId(stationId);
        if (!stationVehicles.isEmpty()) {
            throw new RuntimeException("Cannot delete station with active vehicles. Please transfer vehicles first.");
        }
        
        // Check for active bookings (would need to implement this check)
        // For now, we'll allow deletion but log a warning
        
        stationRepository.delete(station);
        userService.logAudit(null, "Deleted station " + stationId);
        
        Map<String, Object> response = new HashMap<>();
        response.put("message", "Station deleted successfully");
        response.put("stationId", stationId);
        return response;
    }
}

