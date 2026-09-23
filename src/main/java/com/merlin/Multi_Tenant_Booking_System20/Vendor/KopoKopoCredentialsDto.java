package com.merlin.Multi_Tenant_Booking_System20.Vendor;

import jakarta.validation.constraints.NotBlank;

public record KopoKopoCredentialsDto(
        @NotBlank(message = "Client ID is required")
        String clientId,

        @NotBlank(message = "Client secret is required")
        String clientSecret,

        @NotBlank(message = "Till number is required")
        String tillNumber
) {
}
