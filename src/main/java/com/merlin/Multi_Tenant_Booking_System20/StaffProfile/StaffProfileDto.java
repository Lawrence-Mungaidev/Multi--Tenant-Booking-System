package com.merlin.Multi_Tenant_Booking_System20.StaffProfile;

import com.merlin.Multi_Tenant_Booking_System20.QualifiedServices.QualifiedServices;
import com.merlin.Multi_Tenant_Booking_System20.User.User;
import com.merlin.Multi_Tenant_Booking_System20.Vendor.Vendor;

import java.time.LocalDate;
import java.util.List;

public record StaffProfileDto(
        Long userId,
        List<Long> vendorId,
        LocalDate startDate,
        LocalDate endDate,
        List<Long> qualifiedService
) {
}
