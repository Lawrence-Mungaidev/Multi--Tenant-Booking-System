package com.merlin.Multi_Tenant_Booking_System20.Notifications;

import com.merlin.Multi_Tenant_Booking_System20.Exceptions.ResourceNotFound;
import com.merlin.Multi_Tenant_Booking_System20.User.User;
import com.merlin.Multi_Tenant_Booking_System20.User.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class NotificationsService {

    private final NotificationRepository notificationRepository;
    private final NotificationMapper notificationMapper;
    private final UserRepository userRepository;

    public NotificationResponseDto createNotification(String message, Long senderId, Long receiverId, NotificationType notificationType) {
        User sender = userRepository.findById(senderId)
                .orElseThrow(()-> new ResourceNotFound("User not found"));

        User receiver = userRepository.findById(receiverId)
                .orElseThrow(()-> new ResourceNotFound("User not found"));

        Notifications notification = new  Notifications();
        notification.setMessage(message);
        notification.setSender(sender);
        notification.setReceiver(receiver);
        notification.setNotificationType(notificationType);
        notification.setCreatedAt(LocalDateTime.now());
        notification.setRead(false);

        var savedNotification = notificationRepository.save(notification);

        return notificationMapper.toNotificationResponseDto(savedNotification);

    }

    public List<NotificationResponseDto> findNotificationByReceiver(User authenticatedUser) {
        return notificationRepository.findNotificationsByReceiver(authenticatedUser)
                .stream()
                .map(notificationMapper ::toNotificationResponseDto)
                .toList();
    }

    public List<NotificationResponseDto> findNotificationBySender(User authenticatedUser) {
        return notificationRepository.findNotificationsBySender(authenticatedUser)
                .stream()
                .map(notificationMapper ::toNotificationResponseDto)
                .toList();
    }

    public void readNotification(Long notificationId, User authenticatedUser) {
        Notifications notifications = notificationRepository.findById(notificationId)
                .orElseThrow(()-> new ResourceNotFound("Notification not found"));

        if(notifications.isRead()){
            throw new ResourceNotFound("Notification is already read");
        }

        notifications.setRead(true);

        notificationRepository.save(notifications);
    }
}
