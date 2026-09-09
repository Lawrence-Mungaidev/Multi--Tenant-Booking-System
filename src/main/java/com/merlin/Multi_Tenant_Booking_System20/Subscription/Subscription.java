package com.merlin.Multi_Tenant_Booking_System20.Subscription;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.merlin.Multi_Tenant_Booking_System20.User.User;
import com.merlin.Multi_Tenant_Booking_System20.Vendor.Vendor;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Setter
@Getter
public class Subscription {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @ManyToOne
    @JoinColumn(
            name = "vendorId"
    )
    @JsonBackReference
    private Vendor  vendor;
    @Enumerated(EnumType.STRING)
    private Status status;
    private LocalDateTime startDate;
    private LocalDateTime endDate;
    private BigDecimal amount;
    private String mpesaRefference;
}
