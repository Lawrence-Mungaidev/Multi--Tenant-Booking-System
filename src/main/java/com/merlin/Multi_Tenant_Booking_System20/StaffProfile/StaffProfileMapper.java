package com.merlin.Multi_Tenant_Booking_System20.StaffProfile;

import org.springframework.stereotype.Component;

@Component
public class StaffProfileMapper {

    public StaffProfile toStaffProfile(StaffProfileDto dto){
        StaffProfile staffProfile = new StaffProfile();
        staffProfile.setAvailable(true);
    }
}
