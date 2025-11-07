package entity;

import jakarta.persistence.*;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Table(name = "Deposit")
@Data
public class Deposit {
    @Id
    @GeneratedValue(strategy = jakarta.persistence.GenerationType.IDENTITY)
    private Integer depositId;
    @ManyToOne
    @JoinColumn(name = "BookingID")
    private Booking booking;
    private BigDecimal amount;
    private String status = "Held";
    private LocalDateTime createdAt = LocalDateTime.now();
}
