package com.merlin.Multi_Tenant_Booking_System20.Services;

import com.merlin.Multi_Tenant_Booking_System20.User.User;
import com.merlin.Multi_Tenant_Booking_System20.Vendor.Vendor;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ServicesRepository extends JpaRepository<Services,Long> {

    List<Services> findAllServicesByVendor(Vendor vendor);
    List<Services> findAllServicesByVendorAndIsActive(Vendor vendor, Boolean isActive);
}
