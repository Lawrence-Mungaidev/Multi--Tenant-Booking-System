package com.merlin.Multi_Tenant_Booking_System20.StaffProfile;

import com.merlin.Multi_Tenant_Booking_System20.QualifiedServices.QualifiedServices;
import com.merlin.Multi_Tenant_Booking_System20.Vendor.Vendor;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Component
public class StaffProfileMapper {

    public StaffProfile toStaffProfile(StaffProfileDto dto){


        return staffProfile;
    }

    public StaffProfileResponseDto toStaffProfileResponseDto(StaffProfile staffProfile){
        List<Long> qualifiedStaffIds = new ArrayList<>();

        for (QualifiedServices qs : staffProfile.getQualifiedService() ){
            qualifiedStaffIds.add(qs.getId());
        }

        List<Long> vendorIds = new ArrayList<>();

        for (Vendor vendor : staffProfile.getVendor() ){
            vendorIds.add(vendor.getVendorId());
        }

        return new StaffProfileResponseDto(staffProfile.getStaffProfileId(), staffProfile.getUserId().getUserId(),vendorIds,staffProfile.isAvailable(),staffProfile.getStartDate(),staffProfile.getEndDate(),qualifiedStaffIds);
    }
}
