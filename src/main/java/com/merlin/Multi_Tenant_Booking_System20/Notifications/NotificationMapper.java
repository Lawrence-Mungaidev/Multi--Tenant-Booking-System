package com.merlin.Multi_Tenant_Booking_System20.Notifications;

import org.springframework.stereotype.Component;


@Component
public class NotificationMapper {

    public Notifications toNotifications(NotificationDto dto) {
        Notifications notifications = new Notifications();
        notifications.setMessage(dto.message());
        notifications.setNotificationType(dto.notificationType());

        return notifications;
    }

    public NotificationResponseDto toNotificationResponseDto(Notifications notifications) {
        return new NotificationResponseDto(notifications.getId(), notifications.getMessage(), notifications.getSender().getUserId(),notifications.getReceiver().getUserId(), notifications.getNotificationType());
    }
}
