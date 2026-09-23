package com.merlin.Multi_Tenant_Booking_System20.StaffProfile;

import com.merlin.Multi_Tenant_Booking_System20.User.Role;
import com.merlin.Multi_Tenant_Booking_System20.User.User;
import com.merlin.Multi_Tenant_Booking_System20.Vendor.Vendor;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface StaffProfileRepository extends JpaRepository<StaffProfile, Long> {

    Optional<StaffProfile> findByUserIdAndEndDateIsNull(Long userId);
    List<StaffProfile> findStaffProfileByVendorId(Long vendorId);
    Optional<StaffProfile> findByUserId(Long userId);
    Optional<StaffProfile> findByUserAndVendorAndEndDateIsNull(User user, Vendor vendor);

}
