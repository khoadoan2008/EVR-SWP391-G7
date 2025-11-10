package entity;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Table(name = "Station")
@Data
public class Station {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "StationID")
    private Integer stationId;
    private String name;
    private String address;
    private String contactNumber;
    private Integer totalSlots;
    @Column(name = "AvailableSlots")
    private Integer availableSlots;
    private String operatingHours;

    // Geo location for map/nearby queries
    private Double latitude;
    private Double longitude;
}
