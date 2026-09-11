package com.merlin.Multi_Tenant_Booking_System20.ScheduleTime;

import java.time.LocalTime;

public record ServiceScheduleResponseDto(
        Long id,
        DaysOfWeek daysOfWeek,
        LocalTime startTime,
        LocalTime endTime,
        int duration
) {
}
