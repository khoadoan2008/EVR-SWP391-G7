package entity;

import jakarta.persistence.*;
import lombok.Data;
import jakarta.validation.constraints.Pattern;

import java.time.LocalDateTime;

@Data
@Entity
@Table(name = "Complaint")
public class Complaint {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer complaintId;
    @ManyToOne
    @JoinColumn(name = "ContractID")
    private Contract contract;
    @ManyToOne
    @JoinColumn(name = "UserID")
    private User user;
    @ManyToOne
    @JoinColumn(name = "StaffID")
    private User staff;
    private String issueDescription;
    private LocalDateTime createdAt = LocalDateTime.now();
    @Column(name = "Status")
    @Pattern(regexp = "Pending|Resolved|Rejected",
            message = "Status must be: Pending, Resolved, or Rejected")
    private String status = "Pending";
}