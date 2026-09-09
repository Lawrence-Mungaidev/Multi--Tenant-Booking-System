package com.merlin.Multi_Tenant_Booking_System20.Review;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.merlin.Multi_Tenant_Booking_System20.Booking.Booking;
import com.merlin.Multi_Tenant_Booking_System20.User.User;
import com.merlin.Multi_Tenant_Booking_System20.Vendor.Vendor;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
public class Review {

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
    @ManyToOne
    @JoinColumn(
            name = "vendorId"
    )
    @JsonBackReference
    private Vendor vendor;
    private String message;
    private double rating;

    public Review() {
    }

    public Review(String message, double rating) {
        this.message = message;
        this.rating = rating;
    }
}
