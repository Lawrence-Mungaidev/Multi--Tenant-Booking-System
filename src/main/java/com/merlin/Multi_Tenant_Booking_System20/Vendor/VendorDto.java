package com.merlin.Multi_Tenant_Booking_System20.Vendor;

import com.merlin.Multi_Tenant_Booking_System20.User.User;

import java.time.DayOfWeek;
import java.time.LocalDateTime;
import java.time.LocalTime;

public record VendorDto(
        String vendorName,
        String vendorAddress,
        String vendorEmail,
        String vendorPhone,
        String vendorCity,
        DayOfWeek startOfWeek,
        DayOfWeek endOfWeek,
        LocalTime openingHours,
        LocalTime closingHours,
        int maxBookingDays

) {
}
