package com.merlin.Multi_Tenant_Booking_System20.Notifications;

import com.merlin.Multi_Tenant_Booking_System20.User.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface NotificationRepository extends JpaRepository<Notifications, Long>
{

    List<Notifications> findNotificationsByReceiver(User authenticatedUser);
    List<Notifications> findNotificationsBySender(User authenticatedUser);
}
