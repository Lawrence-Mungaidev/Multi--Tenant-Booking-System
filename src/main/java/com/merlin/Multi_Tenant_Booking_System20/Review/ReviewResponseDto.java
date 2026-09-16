package com.merlin.Multi_Tenant_Booking_System20.Review;

public record ReviewResponseDto(
        Long reviewId,
        String message,
        double rating,
        Long vendorId,
        Long clientID,
        Long bookingId
) {
}
