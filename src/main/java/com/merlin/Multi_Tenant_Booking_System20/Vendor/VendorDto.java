package com.merlin.Multi_Tenant_Booking_System20.Vendor;

import com.merlin.Multi_Tenant_Booking_System20.User.User;

import java.time.LocalDateTime;

public record VendorDto(
        String vendorName,
        String vendorAddress,
        String vendorEmail,
        String vendorPhone,
        String vendorCity,
        String startOfWeek,
        String endOfWeek,
        String openingHours,
        String closingHours,
        int maxBookingDays

) {
}
