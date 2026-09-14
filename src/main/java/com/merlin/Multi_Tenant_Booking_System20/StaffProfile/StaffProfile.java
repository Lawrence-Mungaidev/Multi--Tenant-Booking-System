package com.merlin.Multi_Tenant_Booking_System20.StaffProfile;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.merlin.Multi_Tenant_Booking_System20.QualifiedServices.QualifiedServices;
import com.merlin.Multi_Tenant_Booking_System20.User.User;
import com.merlin.Multi_Tenant_Booking_System20.Vendor.Vendor;
import com.merlin.Multi_Tenant_Booking_System20.QualifiedServices.QualifiedServices;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Entity
@Getter
@Setter
public class StaffProfile {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long staffProfileId;
    private String imageProfileURL;

    @OneToOne
    private User userId;


    @OneToMany(
            mappedBy ="staffProfile"
    )
    @JsonManagedReference
    private List<Vendor> vendor;

    private boolean isAvailable;
    private LocalDateTime startDate;
    private LocalDateTime endDate;

    @OneToMany(
            mappedBy = "staffProfile"
    )
    @JsonManagedReference
    private List<QualifiedServices> qualifiedService;

    public StaffProfile() {
    }

    public StaffProfile(User userId, List<Vendor> vendor, boolean isAvailable, LocalDateTime startDate, LocalDateTime endDate, List<QualifiedServices> qualifiedService) {
        this.userId = userId;
        this.vendor = vendor;
        this.isAvailable = isAvailable;
        this.startDate = startDate;
        this.endDate = endDate;
        this.qualifiedService = qualifiedService;
    }
}
