package entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.Pattern;
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

    @Pattern(regexp = "Open|InProgress|Closed", message = "Status must be 'Open', 'InProgress', or 'Closed'")
    private String status;

    private LocalDateTime scheduledAt;
    private LocalDateTime closedAt;

    private String remarks;
}
