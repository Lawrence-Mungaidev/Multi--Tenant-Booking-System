package com.merlin.Multi_Tenant_Booking_System20.StaffProfile;

import com.merlin.Multi_Tenant_Booking_System20.User.Role;
import com.merlin.Multi_Tenant_Booking_System20.User.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface StaffProfileRepository extends JpaRepository<StaffProfile, Long> {

    Optional<User> findByUserIdAndEndDateIsNull(Long userId);
    List<StaffProfile> findStaffProfileByVendorId(Long vendorId);
}
