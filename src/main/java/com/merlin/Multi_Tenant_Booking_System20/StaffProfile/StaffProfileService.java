package com.merlin.Multi_Tenant_Booking_System20.StaffProfile;

import com.merlin.Multi_Tenant_Booking_System20.Cloudinary.MediaService;
import com.merlin.Multi_Tenant_Booking_System20.Exceptions.BusinessRuleException;
import com.merlin.Multi_Tenant_Booking_System20.Exceptions.ResourceNotFound;
import com.merlin.Multi_Tenant_Booking_System20.Notifications.NotificationType;
import com.merlin.Multi_Tenant_Booking_System20.Notifications.NotificationsService;
import com.merlin.Multi_Tenant_Booking_System20.User.*;
import com.merlin.Multi_Tenant_Booking_System20.Vendor.Vendor;
import com.merlin.Multi_Tenant_Booking_System20.Vendor.VendorRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class StaffProfileService {

    private final StaffProfileRepository staffProfileRepository;
    private final StaffProfileMapper staffProfileMapper;
    private final UserRepository userRepository;
    private final MediaService mediaService;
    private final UserMapper userMapper;
    private final NotificationsService notificationsService;
    private final VendorRepository vendorRepository;

    public  StaffProfileResponseDto hireStaff(User staff , Vendor vendor){
        User targetUser = userRepository.findById(staff.getUserId())
                .orElseThrow(() -> new ResourceNotFound("User not found"));

        if (targetUser.getRole().equals(Role.ADMIN)) {
            throw new BusinessRuleException("An admin cannot be hired as staff");
        }

        boolean alreadyEmployed = staffProfileRepository
                . findByUserIdAndEndDateIsNull(targetUser.getUserId())
                .isPresent();

        if (alreadyEmployed) {
            throw new BusinessRuleException("This user is already employed elsewhere");
        }


        StaffProfile staffProfile = new StaffProfile();
        staffProfile.setAvailable(true);
        staffProfile.setStartDate(LocalDateTime.now());
        staffProfile.setUserId(staff);
        staffProfile.setVendor(vendor);

        var savedStaffProfile = staffProfileRepository.save(staffProfile);

        return staffProfileMapper.toStaffProfileResponseDto(savedStaffProfile);

    }


    public void updateProfilePic(Long staffProfileId, MultipartFile file, User authenticatedUser){
        StaffProfile staffProfile = staffProfileRepository.findById(staffProfileId)
                .orElseThrow(() -> new ResourceNotFound("User not found"));

        boolean isAllowed = authenticatedUser.getRole().equals(Role.OWNER)
                || authenticatedUser.getRole().equals(Role.MANAGER);

        if (!isAllowed) {
            throw new BusinessRuleException("You are not allowed to change profile picture");
        }

        String imageUrl = mediaService.uploadImage(file);
        staffProfile.setImageProfileURL(imageUrl);

        staffProfileRepository.save(staffProfile);
    }

    public void  fireStaff(Long staffProfileId, User authenticatedUser){
        boolean isAllowed = authenticatedUser.getRole().equals(Role.OWNER)
                || authenticatedUser.getRole().equals(Role.MANAGER);

        if (!isAllowed) {
            throw new BusinessRuleException("You are not allowed perform this action");
        }

        StaffProfile staffProfile = staffProfileRepository.findById(staffProfileId)
                .orElseThrow(() -> new ResourceNotFound("User not found"));

        staffProfile.setEndDate(LocalDateTime.now());
        staffProfile.setAvailable(false);



        String message = "Unfortunately we no longer need you're service and from " + LocalDateTime.now() + " you are dismissed at " + staffProfile.getVendor().getVendorName() + " we wish you luck on your career.";

        notificationsService.createNotification(message, authenticatedUser.getUserId(), staffProfileId, NotificationType.FIRED);

        staffProfileRepository.save(staffProfile);
    }

    public List<UserResponseDto> getStaffAtVendor(User authenticatedUser, Long vendorId){
        if(!authenticatedUser.getRole().equals(Role.OWNER)){
            throw new BusinessRuleException("Sorry you cannot get this");
        }

        Vendor vendor = vendorRepository.findById(vendorId)
                .orElseThrow(()-> new ResourceNotFound("Cannot vendor"));

        if(!vendor.getBusinessOwner().equals(authenticatedUser)){
            throw new BusinessRuleException("You are not the owner of this vendor");
        }

        return userRepository.findUserByVendorsAndRole(vendor,Role.STAFF)
                .stream()
                .map(userMapper :: toUserResponseDto)
                .toList();

    }

    public StaffProfileResponseDto getStaffProfile(User authenticatedUser){
        Long staffProfileId = authenticatedUser.getStaffProfile().getStaffProfileId();

        StaffProfile staffProfile = staffProfileRepository.findById(staffProfileId)
                .orElseThrow(()-> new ResourceNotFound("User not found"));

        return staffProfileMapper.toStaffProfileResponseDto(staffProfile);
    }
}
