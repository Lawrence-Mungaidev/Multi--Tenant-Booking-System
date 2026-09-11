package com.merlin.Multi_Tenant_Booking_System20.Services;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.merlin.Multi_Tenant_Booking_System20.Category.Category;
import com.merlin.Multi_Tenant_Booking_System20.ScheduleTime.ScheduleTime;
import com.merlin.Multi_Tenant_Booking_System20.Vendor.Vendor;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@Entity
@Getter
@Setter
public class Services {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    private String description;
    private BigDecimal price;
    private int duration;
    private boolean isAvailable;
    private LocalDateTime createdAt;
    @ManyToOne
    @JoinColumn(
            name = "categoryId"
    )
    @JsonBackReference
    private Category category;
    @Enumerated(EnumType.STRING)
    private ServiceType serviceType;

    @OneToMany(
            mappedBy = "services"
    )
    @JsonManagedReference
    private List<ScheduleTime> scheduleTime;
    @ManyToOne
    @JoinColumn(
            name = "vendorId"
    )
    @JsonBackReference
    private Vendor vendor;



    public Services() {
    }

    public Services(String name, String description, BigDecimal price, int duration, LocalDateTime createdAt, Category category, ServiceType serviceType) {
        this.name = name;
        this.description = description;
        this.price = price;
        this.duration = duration;
        this.isAvailable = true;
        this.createdAt = createdAt;
        this.category = category;
        this.serviceType = serviceType;
    }
}
