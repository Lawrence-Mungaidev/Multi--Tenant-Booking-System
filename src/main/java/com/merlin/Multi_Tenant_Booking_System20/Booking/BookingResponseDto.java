package com.merlin.Multi_Tenant_Booking_System20.Booking;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public record BookingResponseDto(
        Long bookingId,
        Long serviceId,
        LocalDateTime intendedDay,
        BookingType bookingType,
        Long bookedStaffId,
        int numberOfPeople,
        BigDecimal amount
) {
}
