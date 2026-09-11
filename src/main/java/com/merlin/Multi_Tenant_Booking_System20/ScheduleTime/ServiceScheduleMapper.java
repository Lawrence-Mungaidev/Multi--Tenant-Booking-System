package com.merlin.Multi_Tenant_Booking_System20.ScheduleTime;

import org.springframework.stereotype.Component;

@Component
public class ServiceScheduleMapper {

    public ServiceScheduleResponseDto  toServiceScheduleResponseDto(ScheduleTime scheduleTime) {
        return   new ServiceScheduleResponseDto(scheduleTime.getId(), scheduleTime.getDaysOfWeek(), scheduleTime.getStartTime(), scheduleTime.getEndTime(), scheduleTime.getDuration());
    }
}
