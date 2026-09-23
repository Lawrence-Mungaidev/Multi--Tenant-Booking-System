package com.merlin.Multi_Tenant_Booking_System20.User;

import com.merlin.Multi_Tenant_Booking_System20.Vendor.Vendor;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {

    List<User> findUserByVendorsAndRole(Vendor vendor, Role role);


    Optional<User> findByEmail(String email);
}
