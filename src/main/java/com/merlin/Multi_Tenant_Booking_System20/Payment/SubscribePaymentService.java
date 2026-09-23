package com.merlin.Multi_Tenant_Booking_System20.Payment;

import com.merlin.Multi_Tenant_Booking_System20.User.User;
import com.merlin.Multi_Tenant_Booking_System20.Vendor.Vendor;
import org.springframework.beans.factory.annotation.Value;

import java.util.Map;

public class SubscribePaymentService {

    private final K2AuthService k2AuthService;
    private final K2PaymentClient k2PaymentClient;

    @Value("${k2.subscription-callback-url}")
    private String subscriptionCallbackUrl;

    @Value("${k2.platform-till-number}")
    private String platformTillNumber;

    @Value("${subscription.fee}")
    private String subscriptionFee;

    public SubscribePaymentService(K2AuthService k2AuthService, K2PaymentClient k2PaymentClient) {
        this.k2AuthService = k2AuthService;
        this.k2PaymentClient = k2PaymentClient;
    }

    public String initiateSubscriptionPayment(Vendor vendor) {
        String token = k2AuthService.getPlatformToken();
        User owner = vendor.getBusinessOwner();

        return k2PaymentClient.initiateStkPush(
                token,
                platformTillNumber, // your own till, from application.yml
                owner.getFirstName(),
                owner.getLastName(),
                owner.getPhoneNumber(),
                owner.getEmail(),
                subscriptionFee.toString(),
                subscriptionCallbackUrl,
                Map.of("vendor_id", vendor.getVendorId().toString(), "type", "subscription")
        );
    }
}
