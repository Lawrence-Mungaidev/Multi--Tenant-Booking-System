package com.merlin.Multi_Tenant_Booking_System20.QualifiedServices;

import com.merlin.Multi_Tenant_Booking_System20.Services.Services;
import com.merlin.Multi_Tenant_Booking_System20.StaffProfile.StaffProfile;

public record QualifiedServiceDto(
        Long staffProfileId,
        Long servicesId
) {
}
