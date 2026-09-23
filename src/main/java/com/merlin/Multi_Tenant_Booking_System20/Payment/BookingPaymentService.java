package com.merlin.Multi_Tenant_Booking_System20.Payment;

import com.merlin.Multi_Tenant_Booking_System20.Booking.Booking;
import com.merlin.Multi_Tenant_Booking_System20.User.User;
import com.merlin.Multi_Tenant_Booking_System20.Vendor.Vendor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.Map;

@Service
public class BookingPaymentService {



    private final K2PaymentClient  k2PaymentClient;
    private final K2AuthService  k2AuthService;

    @Value("${k2.booking-callback-url}")
    private String callbackUrl;

    public BookingPaymentService(K2PaymentClient k2PaymentClient, K2AuthService k2AuthService) {
        this.k2PaymentClient = k2PaymentClient;
        this.k2AuthService = k2AuthService;
    }

    public String initiatePayment(Booking booking) {

        Vendor vendor = booking.getVendor();
        User client = booking.getClient();

        String token = k2AuthService.getTokenForVendor(vendor.getVendorId());

        return k2PaymentClient.initiateStkPush(
                token,
                vendor.getTillNumber(),
                client.getFirstName(),
                client.getLastName(),
                client.getPhoneNumber(),
                client.getEmail(),
                booking.getAmount().toString(),
                callbackUrl,
                Map.of("booking_id", booking.getId().toString(), "reference", "BOOK-" + booking.getId())
        );
    }



}
