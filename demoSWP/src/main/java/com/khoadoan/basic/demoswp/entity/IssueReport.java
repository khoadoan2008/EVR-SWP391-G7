package com.khoadoan.basic.demoswp.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.Pattern;
import lombok.Data;

import java.time.LocalDateTime;

@Entity
@Table(name = "IssueReport")
@Data
public class IssueReport {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer issueReportId;

    @ManyToOne
    @JoinColumn(name = "VehicleID")
    private Vehicle vehicle;

    @ManyToOne
    @JoinColumn(name = "UserID")
    private User reportedBy;

    @ManyToOne
    @JoinColumn(name = "StationID")
    private Station station;

    @Pattern(regexp = "mechanical|electrical|cosmetic|safety", message = "Invalid issue category")
    private String issueCategory;

    @Pattern(regexp = "low|medium|high|critical", message = "Invalid priority level")
    private String priority;

    private String description;
    private String photos;

    @Pattern(regexp = "Open|InProgress|Resolved|Closed", message = "Invalid status")
    private String status = "Open";

    private LocalDateTime reportedAt = LocalDateTime.now();
    private LocalDateTime resolvedAt;
    private String resolutionNotes;
    private String assignedTo;
}
