package com.merlin.Multi_Tenant_Booking_System20.ScheduleTime;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.merlin.Multi_Tenant_Booking_System20.Services.Services;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
import java.time.LocalTime;

@Entity
@Getter
@Setter
public class ScheduleTime {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Enumerated(EnumType.STRING)
    private DaysOfWeek daysOfWeek;
    private LocalTime startTime;
    private LocalTime endTime;
    private int duration;

    @ManyToOne
    @JoinColumn(
            name = "serviceId"
    )
    @JsonBackReference
    private Services services;

    public ScheduleTime() {
    }

    public ScheduleTime(DaysOfWeek daysOfWeek, LocalTime startTime, LocalTime endTime, int duration) {
        this.daysOfWeek = daysOfWeek;
        this.startTime = startTime;
        this.endTime = endTime;
        this.duration = duration;
    }
}
