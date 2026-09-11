package com.merlin.Multi_Tenant_Booking_System20.Services;

import com.merlin.Multi_Tenant_Booking_System20.Category.Category;
import com.merlin.Multi_Tenant_Booking_System20.ScheduleTime.ScheduleTime;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

public record ServiceDto (
        String name,
        String description,
        BigDecimal price,
        List<ScheduleTime> scheduleTime,
        int duration,
        Long category,
        ServiceType serviceType
) {
}
