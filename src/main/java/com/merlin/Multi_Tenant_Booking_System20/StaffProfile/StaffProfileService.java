package com.merlin.Multi_Tenant_Booking_System20.StaffProfile;

import com.merlin.Multi_Tenant_Booking_System20.Cloudinary.MediaService;
import com.merlin.Multi_Tenant_Booking_System20.Exceptions.BusinessRuleException;
import com.merlin.Multi_Tenant_Booking_System20.Exceptions.ResourceNotFound;
import com.merlin.Multi_Tenant_Booking_System20.User.Role;
import com.merlin.Multi_Tenant_Booking_System20.User.User;
import com.merlin.Multi_Tenant_Booking_System20.User.UserRepository;
import com.merlin.Multi_Tenant_Booking_System20.Vendor.Vendor;
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

    public  StaffProfileResponseDto hireStaff(User staff , Vendor vendor){
        User targetUser = userRepository.findById(staff.getUserId())
                .orElseThrow(() -> new ResourceNotFound("User not found"));

        if (targetUser.getRole().equals(Role.ADMIN)) {
            throw new BusinessRuleException("An admin cannot be hired as staff");
        }

        boolean alreadyEmployed = staffProfileRepository
                .findByUserIdAndEndDateIsNull(targetUser.getUserId())
                .isPresent();

        if (alreadyEmployed) {
            throw new BusinessRuleException("This user is already employed elsewhere");
        }

        List<Vendor> vendors = new ArrayList<>();
        vendors.add(vendor);

        StaffProfile staffProfile = new StaffProfile();
        staffProfile.setAvailable(true);
        staffProfile.setStartDate(LocalDateTime.now());
        staffProfile.setUserId(staff);
        staffProfile.setVendor(vendors);

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

        staffProfileRepository.save(staffProfile);
    }

    public List<StaffProfileResponseDto> getStaffs(User authenticatedUser){
        boolean isAllowed = authenticatedUser.getRole().equals(Role.OWNER)
                || authenticatedUser.getRole().equals(Role.MANAGER);

        if (!isAllowed) {
            throw new BusinessRuleException("You are not allowed perform this action");
        }

        Long vendorId= authenticatedUser.getVendor().getVendorId();

        return staffProfileRepository.findStaffProfileByVendorId(vendorId)
                .stream()
                .map(staffProfileMapper :: toStaffProfileResponseDto)
                .toList();
    }

    public StaffProfileResponseDto getStaffProfile(Long staffProfileId,  User authenticatedUser){
        StaffProfile staffProfile = staffProfileRepository.findById(staffProfileId)
                .orElseThrow(()-> new ResourceNotFound("User not found"));

        if (!staffProfile.getUserId().getUserId().equals(authenticatedUser.getUserId()) ) {
            throw new BusinessRuleException("You cannot view this ");
        }

        return staffProfileMapper.toStaffProfileResponseDto(staffProfile);
    }
}
