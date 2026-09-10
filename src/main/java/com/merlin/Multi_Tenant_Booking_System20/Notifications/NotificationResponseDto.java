package com.merlin.Multi_Tenant_Booking_System20.Notifications;

public record NotificationResponseDto(
        Long id,
        String message,
        Long sender,
        Long receiver,
        NotificationType notificationType
) {
}
