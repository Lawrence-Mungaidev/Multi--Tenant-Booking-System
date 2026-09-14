package com.merlin.Multi_Tenant_Booking_System20.StaffProfile;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

public record StaffProfileResponseDto(
        Long staffProfileId,
        Long userId,
        List<Long> vendorId,
        boolean isAvailable,
        LocalDateTime startDate,
        LocalDateTime endDate,
        List<Long> qualifiedService
) {
}
