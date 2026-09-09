package com.merlin.Multi_Tenant_Booking_System20.Booking;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.merlin.Multi_Tenant_Booking_System20.BookingParticipant.BookingParticipant;
import com.merlin.Multi_Tenant_Booking_System20.Payment.Payment;
import com.merlin.Multi_Tenant_Booking_System20.Review.Review;
import com.merlin.Multi_Tenant_Booking_System20.User.User;
import com.merlin.Multi_Tenant_Booking_System20.Vendor.Vendor;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.List;

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
    private User bookedStaff;
    private int numberOfPeople;
    private int rescheduleCount;

    @OneToMany(
            cascade = CascadeType.ALL,
            mappedBy = "booking"
        )
    @JsonManagedReference
    private List<BookingParticipant> bookingParticipant;
    @Enumerated(EnumType.STRING)
    private Status status;
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
    private LocalDateTime bookedDate;

    @OneToOne
    private Payment payment;
    private boolean Attended;
    @OneToOne
    private Review review;

    public Booking() {
    }

    public Booking(LocalDateTime intendentDay, BookingType bookingType, User bookedStaff, int numberOfPeople, int rescheduleCount,  Payment payment) {
        this.intendentDay = intendentDay;
        this.bookingType = bookingType;
        this.bookedStaff = bookedStaff;
        this.numberOfPeople = numberOfPeople;
        this.rescheduleCount = rescheduleCount;
        this.bookedDate = LocalDateTime.now();
        this.payment = payment;
        Attended = false;
    }
}
