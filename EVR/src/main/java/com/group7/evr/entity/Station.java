package com.group7.evr.entity;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Table(name = "Station")
@Data
public class Station {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer stationId;
    private String name;
    private String address;
    private String contactNumber;
    private Integer totalSlots;
    private Integer availableSlots;
    private String operatingHours;
}
