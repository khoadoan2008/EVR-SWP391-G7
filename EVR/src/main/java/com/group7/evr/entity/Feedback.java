package com.group7.evr.entity;

import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@Entity
@Table(name = "Feedback")
public class Feedback {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer feedbackId;
    @ManyToOne
    @JoinColumn(name = "ContractID")
    private Contract contract;
    @ManyToOne
    @JoinColumn(name = "UserID")
    private User user;
    private String category;
    private Integer stars;
    private String comment;
    private LocalDateTime createdAt = LocalDateTime.now();
}
