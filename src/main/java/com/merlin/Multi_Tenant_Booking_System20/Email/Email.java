package com.merlin.Multi_Tenant_Booking_System20.Email;

import com.merlin.Multi_Tenant_Booking_System20.User.User;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
public class Email {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(
            name = "receiverId"
    )
    private User receiver;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(
            name = "senderId"
    )
    private User sender;
    private String subject;
    private String message;

    public Email() {
    }

    public Email(User receiver, User sender, String subject, String message) {
        this.receiver = receiver;
        this.sender = sender;
        this.subject = subject;
        this.message = message;
    }
}
