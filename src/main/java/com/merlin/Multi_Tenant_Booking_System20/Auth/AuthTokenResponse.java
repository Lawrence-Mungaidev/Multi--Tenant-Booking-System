package com.merlin.Multi_Tenant_Booking_System20.Auth;

import com.merlin.Multi_Tenant_Booking_System20.User.Role;

public record AuthTokenResponse(
        Role role,
        String token,
        String firstName,
        boolean mustChangePassword
) {
}
