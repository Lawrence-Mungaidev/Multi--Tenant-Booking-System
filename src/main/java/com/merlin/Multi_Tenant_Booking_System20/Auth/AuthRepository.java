package com.merlin.Multi_Tenant_Booking_System20.Auth;

import com.merlin.Multi_Tenant_Booking_System20.User.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AuthRepository extends JpaRepository<User,Long> {
}
