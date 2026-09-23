package com.merlin.Multi_Tenant_Booking_System20.Booking;

import com.merlin.Multi_Tenant_Booking_System20.User.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

public interface BookingRepository extends JpaRepository<Booking, Long> {

    boolean existsOverlappingBooking (Long staffId, LocalDateTime startTime,  LocalDateTime endTime);
    List<Booking> findByClientId(Long clientId);

    List<Booking> findByBookedStaffUserId(Long bookedStaffId);

    List<Booking> findByVendorId(Long vendorId);



}
