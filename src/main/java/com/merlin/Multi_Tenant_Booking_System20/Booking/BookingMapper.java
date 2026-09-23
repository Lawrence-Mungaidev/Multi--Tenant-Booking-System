package com.merlin.Multi_Tenant_Booking_System20.Booking;

import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

@Component
public class BookingMapper {

    public Booking toBooking(BookingDto dto){
        Booking booking = new Booking();
        booking.setBookingType(dto.bookingType());
        booking.setStatus(BookingStatus.Booking_PENDING);
        booking.setCreatedAT(LocalDateTime.now());

        return booking;
    }

    public BookingResponseDto toBookingResponseDto(Booking booking){
        return new BookingResponseDto(booking.getId(),booking.getIntendentDay(),booking.getBookingType(),booking.getBookedStaff().getUserId(), booking.getNumberOfPeople(), booking.getAmount());
    }
}
