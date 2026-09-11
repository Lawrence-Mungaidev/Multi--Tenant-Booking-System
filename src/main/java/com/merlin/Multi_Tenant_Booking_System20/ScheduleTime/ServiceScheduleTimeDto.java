package com.merlin.Multi_Tenant_Booking_System20.ScheduleTime;

import java.time.DayOfWeek;
import java.time.LocalTime;

public record ServiceScheduleTimeDto(
        Long ServiceId,
        DayOfWeek dayOfWeek,
        LocalTime startTime,
        LocalTime endTime,
        int duration
) {
}
