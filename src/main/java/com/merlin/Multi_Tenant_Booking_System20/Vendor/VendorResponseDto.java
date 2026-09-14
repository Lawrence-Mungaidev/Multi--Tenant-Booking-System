package com.merlin.Multi_Tenant_Booking_System20.Vendor;

import java.time.DayOfWeek;
import java.time.LocalTime;

public record VendorResponseDto(
        Long id,
        String vendorName,
        String imageURL,
        String vendorAddress,
        String vendorEmail,
        String vendorPhone,
        String vendorCity,
        DayOfWeek startOfWeek,
        DayOfWeek endOfWeek,
        LocalTime openingHours,
        LocalTime closingHours
) {
}
