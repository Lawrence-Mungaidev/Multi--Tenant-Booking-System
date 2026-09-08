package com.merlin.Multi_Tenant_Booking_System20.Notifications;

import com.merlin.Multi_Tenant_Booking_System20.User.User;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Entity
@Getter
@Setter
public class Notifications {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String message;
    private boolean isRead;
    private LocalDateTime createdAt;

    @ManyToOne
    @JoinColumn(
            name = "senderId"
    )
    private User sender;

    @ManyToOne
    @JoinColumn(
            name = "receiverId"
    )
    private User receiver;

    private NotificationType notificationType;

    public Notifications() {}

    public Notifications(String message, User sender, User receiver, NotificationType notificationType) {
        this.message = message;
        this.isRead = false;
        this.sender = sender;
        this.receiver = receiver;
        this.notificationType = notificationType;
        this.createdAt = LocalDateTime.now();
    }
}
