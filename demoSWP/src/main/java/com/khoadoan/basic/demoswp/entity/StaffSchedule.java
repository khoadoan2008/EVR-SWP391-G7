package com.khoadoan.basic.demoswp.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.Pattern;
import lombok.Data;

import java.time.LocalDateTime;

@Entity
@Table(name = "StaffSchedule")
@Data
public class StaffSchedule {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer scheduleId;

    @ManyToOne
    @JoinColumn(name = "StaffID")
    private User staff;

    @ManyToOne
    @JoinColumn(name = "StationID")
    private Station station;

    private LocalDateTime shiftStart;
    private LocalDateTime shiftEnd;
    @Pattern(regexp = "Morning|Afternoon|Evening|Night", message = "Shift type must be one of: Morning, Afternoon, Evening, Night")
    private String shiftType;
    @Pattern(regexp = "Scheduled|Active|Completed|Cancelled", message = "Status must be one of: Scheduled, Active, Completed, Cancelled")
    private String status = "Scheduled";
    private String notes;
}
