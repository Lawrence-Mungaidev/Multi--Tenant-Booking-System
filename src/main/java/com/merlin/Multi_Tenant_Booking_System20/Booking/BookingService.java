package com.merlin.Multi_Tenant_Booking_System20.Booking;

import com.merlin.Multi_Tenant_Booking_System20.Exceptions.BusinessRuleException;
import com.merlin.Multi_Tenant_Booking_System20.Exceptions.ResourceNotFound;
import com.merlin.Multi_Tenant_Booking_System20.Notifications.NotificationType;
import com.merlin.Multi_Tenant_Booking_System20.Notifications.NotificationsService;
import com.merlin.Multi_Tenant_Booking_System20.Payment.BookingPaymentService;
import com.merlin.Multi_Tenant_Booking_System20.Services.Services;
import com.merlin.Multi_Tenant_Booking_System20.Services.ServicesRepository;
import com.merlin.Multi_Tenant_Booking_System20.StaffProfile.StaffProfile;
import com.merlin.Multi_Tenant_Booking_System20.StaffProfile.StaffProfileRepository;
import com.merlin.Multi_Tenant_Booking_System20.User.Role;
import com.merlin.Multi_Tenant_Booking_System20.User.User;
import com.merlin.Multi_Tenant_Booking_System20.User.UserRepository;
import com.merlin.Multi_Tenant_Booking_System20.Vendor.Vendor;
import com.merlin.Multi_Tenant_Booking_System20.Vendor.VendorRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class BookingService {

    private final BookingRepository bookingRepository;
    private final BookingMapper bookingMapper;
    private final ServicesRepository servicesRepository;
    private final StaffProfileRepository staffProfileRepository;
    private final BookingPaymentService  bookingPaymentService;
    private final NotificationsService notificationsService;
    private final VendorRepository vendorRepository;

    public BookingResponseDto createBooking(BookingDto dto, User authenticatedUser){
        Services service = servicesRepository.findById(dto.serviceId())
                .orElseThrow(() -> new ResourceNotFound("Service not found"));

        StaffProfile staff = staffProfileRepository.findById(dto.bookedStaffId())
                .orElseThrow(() -> new ResourceNotFound("Staff Profile not found"));

        if (!service.isAvailable()) {
            throw new BusinessRuleException("The service is currently not available");
        }

        if (!staff.isAvailable()) {
            throw new BusinessRuleException("The Staff Profile is currently not available, please choose another staff");
        }

        LocalDateTime endTime = dto.startTime().plusMinutes(service.getDuration());

        if (endTime.toLocalTime().isAfter(service.getVendor().getClosingHour())) {
            throw new BusinessRuleException("Booking extends beyond vendor's closing time");
        }

        if (dto.startTime().toLocalDate().isAfter(LocalDate.now().plusDays(service.getVendor().getMaxBookingDays()))) {
            throw new BusinessRuleException("Booking is beyond the allowed advance booking window");
        }

        boolean hasOverlap = bookingRepository.existsOverlappingBooking(staff.getStaffProfileId(), dto.startTime(), endTime);
        if (hasOverlap) {
            throw new BusinessRuleException("Staff member is already booked at this time");
        }

        Booking booking = bookingMapper.toBooking(dto);
        booking.setBookedStaff(staff);
        booking.setServices(service);
        booking.setVendor(service.getVendor());
        booking.setStatus(BookingStatus.Booking_Pending);

        booking.setNumberOfParticipants(
                dto.bookingType().equals(BookingType.GROUP) ? dto.numberOfParticipants() : 1
        );
        booking.setBookingType(dto.bookingType());

        var savedBooking = bookingRepository.save(booking); // save FIRST, so booking.getId() exists

        String paymentLocation = bookingPaymentService.initiatePayment(savedBooking);

        booking.setPaymentReference(paymentLocation);


        return bookingMapper.toBookingResponseDto(savedBooking);
    }

    public void confirmBookingPayment(Long bookingId) {
        Booking booking = bookingRepository.findById(bookingId)
                .orElseThrow(() -> new ResourceNotFound("Booking not found"));

        if (booking.getStatus() == BookingStatus.Booking_Approved) {
            throw new BusinessRuleException("The booking has already been approved");
        }

        booking.setStatus(BookingStatus.Booking_Approved);
        bookingRepository.save(booking);

        String confirmationMessage = booking.getId() + " has been confirmed for " + booking.getServices();

        notificationsService.createNotification(confirmationMessage,null,booking.getClient().getUserId(), NotificationType.BOOKING_SUCCESSFUL);

    }

    public void failBookingPayment(Long bookingId) {
        Booking booking = bookingRepository.findById(bookingId)
                .orElseThrow(() -> new ResourceNotFound("Booking not found"));

        if (booking.getStatus() == BookingStatus.Booking_Failed) {
            throw new BusinessRuleException("The booking has already been failed");
        }

        booking.setStatus(BookingStatus.Booking_Failed);
        bookingRepository.save(booking);
    }

    public List<BookingResponseDto> getMyBookingsAsClient(User authenticatedUser) {
       if(!authenticatedUser.getRole().equals(Role.CLIENT)){
           throw new BusinessRuleException("You are not allowed to perform this since you're not a client");
       }

       return bookingRepository.findByClientId(authenticatedUser.getUserId())
               .stream()
               .map(bookingMapper :: toBookingResponseDto)
               .toList();
    }

    public List<BookingResponseDto> getMyAssignedBookings(User authenticatedUser) {
            if(!authenticatedUser.getRole().equals(Role.STAFF)){
                throw new BusinessRuleException("Only Staff roles can get assigned bookings");
            }

            StaffProfile staffProfile = staffProfileRepository.findByUserIdAndEndDateIsNull(authenticatedUser.getUserId())
                    .orElseThrow(()-> new ResourceNotFound("You're not hired anywhere at the moment"));

            Vendor vendor = staffProfile.getVendor();

            return bookingRepository.findByVendorId(vendor.getVendorId())
                    .stream()
                    .map(bookingMapper::toBookingResponseDto)
                    .toList();
    }


    public List<BookingResponseDto> getVendorsBooking(Long vendorId, User authenticatedUser) {
        Vendor vendor = vendorRepository.findById(vendorId)
                .orElseThrow(() -> new ResourceNotFound("Vendor not found"));

        boolean isOwner = vendor.getBusinessOwner().equals(authenticatedUser);

        boolean isManagerAtThisVendor = authenticatedUser.getRole().equals(Role.MANAGER)
                && staffProfileRepository.findByUserAndVendorAndEndDateIsNull(authenticatedUser, vendor).isPresent();

        if (!(isOwner || isManagerAtThisVendor)) {
            throw new BusinessRuleException("You cannot view bookings for this vendor");
        }

        List<Booking> bookings = bookingRepository.findByVendorId(vendorId);
        return bookings.stream().map(bookingMapper::toBookingResponseDto).toList();
    }
}
