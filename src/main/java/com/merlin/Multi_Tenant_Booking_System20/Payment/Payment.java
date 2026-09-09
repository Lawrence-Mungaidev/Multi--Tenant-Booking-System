package com.merlin.Multi_Tenant_Booking_System20.Payment;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.merlin.Multi_Tenant_Booking_System20.Booking.Booking;
import com.merlin.Multi_Tenant_Booking_System20.User.User;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
public class Payment {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @ManyToOne
    @JoinColumn(
            name = "clientId"
    )
    @JsonBackReference
    private User client;
    @OneToOne
    private Booking booking;
    @Column(unique = true, nullable = false)
    private String mpesaReffrence;
    @Enumerated(EnumType.STRING)
    private Status status;

    public Payment() {
    }

    public Payment(String mpesaReffrence, Status status) {
        this.mpesaReffrence = mpesaReffrence;
        this.status = status;
    }
}
