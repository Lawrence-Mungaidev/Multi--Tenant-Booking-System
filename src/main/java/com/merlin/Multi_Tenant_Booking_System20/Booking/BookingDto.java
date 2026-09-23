package com.merlin.Multi_Tenant_Booking_System20.Booking;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

public record BookingDto(
        Long serviceId,
        BookingType bookingType,
        Long bookedStaffId,
        LocalDateTime startTime,
        Long scheduleTimeId,
        int numberOfParticipants

) {
}
