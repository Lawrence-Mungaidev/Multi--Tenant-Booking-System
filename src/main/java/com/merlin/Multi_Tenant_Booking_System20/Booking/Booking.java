package com.merlin.Multi_Tenant_Booking_System20.Booking;

import com.merlin.Multi_Tenant_Booking_System20.BookingParticipant.BookingParticipant;
import com.merlin.Multi_Tenant_Booking_System20.User.User;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

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
    private User bookedStaff;
    private int numberOfPeople;
    private int rescheduleCount;


    private BookingParticipant bookingParticipant;



}
