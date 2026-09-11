package com.merlin.Multi_Tenant_Booking_System20.Services;

import com.merlin.Multi_Tenant_Booking_System20.Category.Category;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public record ServiceDto (
        String name,
        String description,
        BigDecimal price,
        int duration,
        Long category,
        ServiceType serviceType
) {
}
