package com.merlin.Multi_Tenant_Booking_System20.StaffProfile;

import java.time.LocalDate;
import java.util.List;

public record StaffProfileResponseDto(
        Long staffProfileId,
        Long userId,
        List<Long> vendorId,
        boolean isAvailable,
        LocalDate startDate,
        LocalDate endDate,
        List<Long> qualifiedService
) {
}
