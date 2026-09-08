package com.merlin.Multi_Tenant_Booking_System20.QualifiedServices;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.merlin.Multi_Tenant_Booking_System20.Services.Services;
import com.merlin.Multi_Tenant_Booking_System20.StaffProfile.StaffProfile;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Entity
@Getter
@Setter
public class QualifiedServices {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(
            name = "staffProfileId"
    )
    @JsonManagedReference
    private StaffProfile staffProfile;
    @OneToOne
    private Services services;
    private LocalDateTime createdAt;

    public QualifiedServices() {}

    public QualifiedServices(StaffProfile staffProfile, Services services) {
        this.staffProfile = staffProfile;
        this.services = services;
    }
}
