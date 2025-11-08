package entity;

import jakarta.persistence.*;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Table(name = "Payment")
@Data
public class Payment {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer paymentId;
    @ManyToOne
    @JoinColumn(name = "BookingID")
    private Booking booking;
    private String method;
    private BigDecimal amount;
    private LocalDateTime paymentDate = LocalDateTime.now();
    private String status;

}
