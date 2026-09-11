package com.merlin.Multi_Tenant_Booking_System20.Services;

import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

@Component
public class ServiceMapper {

    public Services toService(ServiceDto dto) {
        Services services = new Services();
        services.setName(dto.name());
        services.setDescription(dto.description());
        services.setPrice(dto.price());
        services.setDuration(dto.duration());
        services.setServiceType(dto.serviceType());
        services.setCreatedAt(LocalDateTime.now());
        services.setAvailable(true);

        return services;
    }

    public ServiceResponseDto toserviceResponseDto(Services services) {
        return new ServiceResponseDto(services.getId(), services.getName(), services.getDescription(), services.getPrice(),services.getDuration(),services.getCategory().getId());
    }
}
