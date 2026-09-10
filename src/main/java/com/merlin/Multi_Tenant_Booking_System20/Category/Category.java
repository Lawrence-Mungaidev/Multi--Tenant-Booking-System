package com.merlin.Multi_Tenant_Booking_System20.Category;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.merlin.Multi_Tenant_Booking_System20.Services.Services;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.Fetch;

import java.time.LocalDateTime;
import java.util.List;

@Entity
@Getter
@Setter
public class Category {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    private String description;
    private LocalDateTime createdAt;

    @OneToMany(
            mappedBy = "category"
    )
    @JsonManagedReference
    private List<Services> services;

    public Category() {
    }

    public Category(String name, String description) {
        this.name = name;
        this.description = description;
        this.createdAt = LocalDateTime.now();
    }
}
