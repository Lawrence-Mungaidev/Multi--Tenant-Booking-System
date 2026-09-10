package com.merlin.Multi_Tenant_Booking_System20.Notifications;

import com.merlin.Multi_Tenant_Booking_System20.User.User;

public record NotificationDto(
        String message,
        Long sender,
        Long receiver,
        NotificationType notificationType
) {
}
