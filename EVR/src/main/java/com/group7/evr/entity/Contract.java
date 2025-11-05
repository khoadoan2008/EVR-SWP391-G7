package com.group7.evr.entity;

import com.group7.evr.enums.ContractStatus;
import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDateTime;

@Entity
@Table(name = "Contract")
@Data
public class Contract {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer contractId;
    @ManyToOne
    @JoinColumn(name = "BookingID")
    private Booking booking;
    private String renterSignature;
    private String staffSignature;
    private LocalDateTime signedAt = LocalDateTime.now();
    
    @Enumerated(EnumType.STRING)
    private ContractStatus status = ContractStatus.ACTIVE;
}
