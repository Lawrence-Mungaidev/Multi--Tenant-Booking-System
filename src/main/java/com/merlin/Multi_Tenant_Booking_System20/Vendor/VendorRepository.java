package com.merlin.Multi_Tenant_Booking_System20.Vendor;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface VendorRepository extends JpaRepository<Vendor,Long> {

    Optional<Vendor> findByBusinessOwnerId(Long ownerId);
}
