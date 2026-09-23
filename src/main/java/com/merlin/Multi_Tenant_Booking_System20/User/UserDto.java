package com.merlin.Multi_Tenant_Booking_System20.User;

import com.merlin.Multi_Tenant_Booking_System20.Vendor.Vendor;

import java.util.List;

public record UserDto(
        String firstName,
        String lastName,
        String phoneNumber,
        String email,
        String password,
        Role role
) {
}
