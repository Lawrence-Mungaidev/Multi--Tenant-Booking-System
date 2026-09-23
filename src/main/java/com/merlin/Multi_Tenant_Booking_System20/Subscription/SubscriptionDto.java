package com.merlin.Multi_Tenant_Booking_System20.Subscription;

import com.merlin.Multi_Tenant_Booking_System20.Vendor.Vendor;

import java.math.BigDecimal;

public record SubscriptionDto(
        Long vendor,
        Status status,
        BigDecimal amount
) {
}
    