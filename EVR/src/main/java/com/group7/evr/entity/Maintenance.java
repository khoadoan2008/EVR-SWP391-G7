package com.group7.evr.entity;

import com.group7.evr.enums.MaintenanceStatus;
import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDateTime;

@Entity
@Table(name = "Maintenance")
@Data
public class Maintenance {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer maintenanceId;

    @ManyToOne
    @JoinColumn(name = "VehicleID")
    private Vehicle vehicle;

    @ManyToOne
    @JoinColumn(name = "StationID")
    private Station station;

    @ManyToOne
    @JoinColumn(name = "StaffID")
    private User staff;

    private String issueDescription;
    
    @Enumerated(EnumType.STRING)
    private MaintenanceStatus status;

    private LocalDateTime scheduledAt;
    private LocalDateTime closedAt;

    private String remarks;
}
