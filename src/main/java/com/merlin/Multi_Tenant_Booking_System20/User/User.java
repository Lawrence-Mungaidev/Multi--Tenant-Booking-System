package com.merlin.Multi_Tenant_Booking_System20.User;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.merlin.Multi_Tenant_Booking_System20.Booking.Booking;
import com.merlin.Multi_Tenant_Booking_System20.BookingParticipant.BookingParticipant;
import com.merlin.Multi_Tenant_Booking_System20.Notifications.Notifications;
import com.merlin.Multi_Tenant_Booking_System20.Payment.Payment;
import com.merlin.Multi_Tenant_Booking_System20.Review.Review;
import com.merlin.Multi_Tenant_Booking_System20.StaffProfile.StaffProfile;
import com.merlin.Multi_Tenant_Booking_System20.Vendor.Vendor;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.jspecify.annotations.Nullable;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.time.LocalDateTime;
import java.util.Collection;
import java.util.List;

@Entity
@Getter
@Setter
public class User implements UserDetails {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long userId;
    private String firstName;
    private String lastName;
    private String phoneNumber;
    @Column(unique = true, nullable = false)
    private String email;
    private String password;

    @Enumerated(EnumType.STRING)
    private Role role;
    private LocalDateTime createdAt;
    private boolean isActive;

    @OneToOne
    private StaffProfile staffProfile;

    @OneToMany(
            mappedBy = "sender"
    )
    @JsonManagedReference
    private List<Notifications> notifications;

    @OneToMany(
            mappedBy = "receiver"
    )
    @JsonManagedReference
    private List<Notifications> notification;

    @OneToMany(
            mappedBy = "bookedStaff"
    )
    @JsonManagedReference
    private List<Booking> booking;

    @OneToMany(
            mappedBy = "client"
    )
    @JsonManagedReference
    private List<BookingParticipant> bookingParticipant;

    @OneToMany(
            mappedBy = "client"
    )
    @JsonManagedReference
    private List<Booking> bookings;

    @OneToMany(
            mappedBy = "client"
    )
    @JsonManagedReference
    private List<Review> reviews;

    @OneToMany(
            mappedBy = "client"
    )
    @JsonManagedReference
    private List<Payment>  payments;


    public User() {
    }

    public User(String firstName, String lastName, String phoneNumber, String email, String password, Role role, List<Vendor> businesses) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.phoneNumber = phoneNumber;
        this.email = email;
        this.password = password;
        this.role = role;
        this.businesses = businesses;
    }

    @OneToMany(
            mappedBy = "businessOwner"
    )
    @JsonManagedReference
    private List<Vendor> businesses;

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return List.of(new SimpleGrantedAuthority("ROLE_" + role.name()));
    }

    @Override
    public @Nullable String getPassword() {
        return password;
    }

    @Override
    public String getUsername() {
        return email;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return isActive;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return isActive;
    }
}
