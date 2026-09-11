package com.merlin.Multi_Tenant_Booking_System20.StaffProfile;

import com.merlin.Multi_Tenant_Booking_System20.User.Role;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface StaffProfileRepository extends JpaRepository<StaffProfile, Long> {


}
