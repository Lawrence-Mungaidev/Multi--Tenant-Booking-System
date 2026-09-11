package com.merlin.Multi_Tenant_Booking_System20.Services;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public record ServiceResponseDto(
        Long id,
        String name,
        String description,
        BigDecimal price,
        int duration,
        Long category
) {
}
