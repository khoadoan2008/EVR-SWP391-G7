package com.khoadoan.basic.demoswp.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.Pattern;
import lombok.Data;

import java.time.LocalDateTime;

@Entity
@Table(name = "RiskFlag")
@Data
public class RiskFlag {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer riskFlagId;

    @ManyToOne
    @JoinColumn(name = "UserID")
    private User user;

    @ManyToOne
    @JoinColumn(name = "FlaggedBy")
    private User flaggedBy;

    private String reason;
    private Integer riskScore; // 1-10 scale
    @Pattern(regexp = "Active|Resolved|Dismissed", message = "Status must be Active, Resolved, or Dismissed")
    private String status = "Active";
    private LocalDateTime flaggedAt = LocalDateTime.now();
    private LocalDateTime resolvedAt;
}
