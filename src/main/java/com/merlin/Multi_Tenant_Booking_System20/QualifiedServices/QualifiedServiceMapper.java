package com.merlin.Multi_Tenant_Booking_System20.QualifiedServices;

import org.springframework.stereotype.Component;

@Component
public class QualifiedServiceMapper {

    public QualifiedServiceResponseDto toQualifiedServiceResponseDto(QualifiedServices qualifiedService) {
        return new QualifiedServiceResponseDto(qualifiedService.getId(), qualifiedService.getStaffProfile().getStaffProfileId(), qualifiedService.getServices().getId());
    }

}
