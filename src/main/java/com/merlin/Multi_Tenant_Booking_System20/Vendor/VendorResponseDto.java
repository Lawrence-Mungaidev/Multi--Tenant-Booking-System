package com.merlin.Multi_Tenant_Booking_System20.Vendor;

public record VendorResponseDto(
        Long id,
        String vendorName,
        String imageURL ,
        String vendorAddress,
        String vendorEmail,
        String vendorPhone,
        String vendorCity,
        String startOfWeek,
        String endOfWeek,
        String openingHours,
        String closingHours
) {
}
