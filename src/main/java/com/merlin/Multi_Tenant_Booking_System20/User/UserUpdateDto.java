package com.merlin.Multi_Tenant_Booking_System20.User;

import java.util.List;

public record UserUpdateDto(

        String firstName,
        String lastName,
        Role role,
        List<Long> vendor
) {
}
