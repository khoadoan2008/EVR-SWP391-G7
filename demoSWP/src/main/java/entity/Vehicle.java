package entity;

import jakarta.persistence.*;
import lombok.Data;

import java.math.BigDecimal;
import java.sql.Date;

@Entity
@Table(name = "Vehicle")
@Data
public class Vehicle {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer vehicleId;
    @ManyToOne
    @JoinColumn(name = "ModelID")
    private Model model;
    @ManyToOne
    @JoinColumn(name = "StationID")
    private Station station;
    private String plateNumber;
    private BigDecimal batteryLevel;
    private BigDecimal mileage;
    private String status = "Available";
    private Date lastMaintenanceDate;
}

