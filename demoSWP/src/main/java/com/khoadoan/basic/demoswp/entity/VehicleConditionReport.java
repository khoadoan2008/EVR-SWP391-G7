package com.khoadoan.basic.demoswp.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.Pattern;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Table(name = "VehicleConditionReport")
@Data
public class VehicleConditionReport {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer reportId;
    @ManyToOne
    @JoinColumn(name = "ContractID")
    private Contract contract;
    @ManyToOne
    @JoinColumn(name = "VehicleID")
    private Vehicle vehicle;
    @ManyToOne
    @JoinColumn(name = "StaffID")
    private User staff;
    private LocalDateTime reportTime = LocalDateTime.now();
    private BigDecimal battery;
    private String damageDescription;
    private String photos; // JSON array of photo URLs

    @Column(name = "ReportType")
    @Pattern(regexp = "PreRental|PostRental", message = "ReportType must be either 'PreRental' or 'PostRental'")
    private String reportType;
}
