package com.merlin.Multi_Tenant_Booking_System20.User;

import java.util.List;

public record UserResponseDto(
        String firstName,
        String lastName,
        Role role,
        Long vendor
) {
}
