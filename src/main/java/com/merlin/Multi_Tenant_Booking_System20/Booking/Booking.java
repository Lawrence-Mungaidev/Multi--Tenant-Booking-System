package com.merlin.Multi_Tenant_Booking_System20.Booking;

import com.fasterxml.jackson.annotation.JsonBackReference;

import com.merlin.Multi_Tenant_Booking_System20.Payment.Payment;
import com.merlin.Multi_Tenant_Booking_System20.Review.Review;
import com.merlin.Multi_Tenant_Booking_System20.Services.Services;
import com.merlin.Multi_Tenant_Booking_System20.StaffProfile.StaffProfile;
import com.merlin.Multi_Tenant_Booking_System20.User.User;
import com.merlin.Multi_Tenant_Booking_System20.Vendor.Vendor;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;


import java.math.BigDecimal;
import java.time.LocalDateTime;


@Entity
@Getter
@Setter
public class Booking {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private LocalDateTime intendentDay;
    @Enumerated(EnumType.STRING)
    private BookingType bookingType;

    @ManyToOne
    @JoinColumn(
            name = "bookedStaffId"
    )
    private StaffProfile bookedStaff;
    private int numberOfParticipants;
    private int rescheduleCount;

    @Enumerated(EnumType.STRING)
    private BookingStatus status;
    @ManyToOne
    @JoinColumn(
            name = "vendorId"
    )
    @JsonBackReference
    private Vendor vendor;

    @ManyToOne
    @JoinColumn(
            name = "clientId"
    )
    @JsonBackReference
    private User client;

    private boolean Attended;
    private LocalDateTime attendedDate;
    @OneToOne
    private Review review;
    private LocalDateTime createdAT;
    private BigDecimal amount;
    @ManyToOne
    @JoinColumn(
            name = "serviceId"
    )
    @JsonBackReference
    private Services services;
    private String paymentReference;


    public Booking() {
    }

    public Booking(LocalDateTime intendentDay, BookingType bookingType, StaffProfile bookedStaff, int numberOfParticipants, int rescheduleCount) {
        this.intendentDay = intendentDay;
        this.bookingType = bookingType;
        this.bookedStaff = bookedStaff;
        this.numberOfParticipants = numberOfParticipants;
        this.rescheduleCount = rescheduleCount;
        this.createdAT = LocalDateTime.now();
        Attended = false;
    }
}
