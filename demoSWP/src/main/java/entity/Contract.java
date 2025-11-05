package entity;

import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDateTime;

@Entity
@Table(name = "Contract")
@Data
public class Contract {
    @Id
    @GeneratedValue(strategy = jakarta.persistence.GenerationType.IDENTITY)
    private Integer contractId;
    @ManyToOne
    @JoinColumn(name = "BookingID")
    private Booking booking;
    private String renterSignature;
    private String staffSignature;
    private LocalDateTime signedAt = LocalDateTime.now();
    private String status = "Active";
}
