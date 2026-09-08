package com.merlin.Multi_Tenant_Booking_System20.BookingParticipant;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.merlin.Multi_Tenant_Booking_System20.Booking.Booking;
import com.merlin.Multi_Tenant_Booking_System20.User.User;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
public class BookingParticipant {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @ManyToOne
    @JoinColumn(
            name = "clientId"
    )
    @JsonBackReference
    private User client;

    @ManyToOne
    @JoinColumn(
            name = "bookingId"
    )
    @JsonBackReference
    private Booking booking;
    private String placeHolderName;
    @Enumerated(EnumType.STRING)
    private Status status;


}
