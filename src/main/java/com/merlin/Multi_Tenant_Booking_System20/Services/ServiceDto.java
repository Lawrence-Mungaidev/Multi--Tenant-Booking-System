package com.merlin.Multi_Tenant_Booking_System20.Services;

import com.merlin.Multi_Tenant_Booking_System20.Category.Category;
import com.merlin.Multi_Tenant_Booking_System20.ScheduleTime.ScheduleTime;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

public record ServiceDto (
        @NotBlank(message = "Product name is required")
        @Pattern(regexp = "^[a-zA-Z0-9 ]{3,}$", message = "Please enter a valid Product Name ")
        String name,

        String description,
        BigDecimal price,
        List<ScheduleTime> scheduleTime,
        int duration,
        Long category,
        ServiceType serviceType
) {
}
