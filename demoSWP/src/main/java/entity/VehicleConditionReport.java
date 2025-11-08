package entity;

import jakarta.persistence.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Table(name = "VehicleConditionReport")
public class VehicleConditionReport {
    @Id
    @GeneratedValue(strategy = jakarta.persistence.GenerationType.IDENTITY)
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
    private String photos;
    private String reportType; // 'PreRental', 'PostRental'
}
