package com.merlin.Multi_Tenant_Booking_System20.Vendor;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.merlin.Multi_Tenant_Booking_System20.Booking.Booking;
import com.merlin.Multi_Tenant_Booking_System20.Review.Review;
import com.merlin.Multi_Tenant_Booking_System20.StaffProfile.StaffProfile;
import com.merlin.Multi_Tenant_Booking_System20.Subscription.Subscription;
import com.merlin.Multi_Tenant_Booking_System20.User.User;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.List;

@Entity
@Getter
@Setter
public class Vendor {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long vendorId;
    private String imageURL;
    private String vendorName;
    private String vendorAddress;
    private String vendorEmail;
    private String vendorPhone;
    private String vendorCity;

    //rlt needed here
    @ManyToOne
    @JoinColumn(
            name = "bsOwnerId"
    )
    @JsonBackReference
    private User businessOwner;
    private LocalDateTime createdAt;
    private String startOfWeek;
    private String endOfWeek;
    private String openingHours;
    private String closingHours;
    private int stars;
    private int maxBookingDays;

    @ManyToOne
    @JoinColumn(
            name = "staffProfileId"
    )
    @JsonBackReference
    private StaffProfile staffProfile;

    @OneToMany(
            mappedBy = "vendor"
    )
    @JsonManagedReference
    private List<Booking> booking;
    @OneToMany(
            mappedBy = "vendor"
    )
    @JsonManagedReference
    private List<Review> reviews;
    private String flutterwaveSubaccountId;

    @OneToMany(
            mappedBy = "vendor"
    )
    @JsonManagedReference
    private Subscription subscription;

    public Vendor() {}

    public Vendor(String vendorName, String imageURL ,String vendorAddress, String vendorEmail, String vendorPhone, String vendorCity, User businessOwner, LocalDateTime createdAt, String startOfWeek, String endOfWeek, String openingHours, String closingHours) {
        this.vendorName = vendorName;
        this.imageURL = imageURL;
        this.vendorAddress = vendorAddress;
        this.vendorEmail = vendorEmail;
        this.vendorPhone = vendorPhone;
        this.vendorCity = vendorCity;
        this.businessOwner = businessOwner;
        this.createdAt = createdAt;
        this.startOfWeek = startOfWeek;
        this.endOfWeek = endOfWeek;
        this.openingHours = openingHours;
        this.closingHours = closingHours;
        this.createdAt = LocalDateTime.now();
    }
}
