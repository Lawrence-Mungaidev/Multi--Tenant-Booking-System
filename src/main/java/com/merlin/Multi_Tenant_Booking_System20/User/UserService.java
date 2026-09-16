package com.merlin.Multi_Tenant_Booking_System20.User;

import com.merlin.Multi_Tenant_Booking_System20.Exceptions.BusinessRuleException;
import com.merlin.Multi_Tenant_Booking_System20.Exceptions.ResourceNotFound;
import com.merlin.Multi_Tenant_Booking_System20.StaffProfile.StaffProfileService;
import com.merlin.Multi_Tenant_Booking_System20.StaffProfile.StaffProfileUpdate;
import com.merlin.Multi_Tenant_Booking_System20.Vendor.Vendor;
import com.merlin.Multi_Tenant_Booking_System20.Vendor.VendorRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final UserMapper userMapper;
    private final PasswordEncoder passwordEncoder;
    private final StaffProfileService  staffProfileService;
    private final VendorRepository vendorRepository;

    public UserResponseDto createStaffS(Long vendorId,UserDto dto, User authenticatedUser) {
        boolean isOwnerOrManager = authenticatedUser.getRole().equals(Role.OWNER) || authenticatedUser.getRole().equals(Role.MANAGER);

        if(!isOwnerOrManager){
            throw new BusinessRuleException("Invalid role");
        }

        Vendor vendor = vendorRepository.findById(vendorId)
                .orElseThrow(()->new ResourceNotFound("Vendor wasn't found"));

        User user = userMapper.toUser(dto);

        String hashedPassword = passwordEncoder.encode(dto.password());

        user.setPassword(hashedPassword);

        if(dto.role().equals(Role.OWNER)){
          throw new BusinessRuleException("Invalid Role");
        }

        boolean isTheRightVendor = false;

        for(Vendor v : authenticatedUser.getVendor()){
            if(v.getVendorId().equals(vendor.getVendorId())){
                isTheRightVendor = true;
            }

        }
        if(!isTheRightVendor){
            throw new BusinessRuleException("You cannot add the user to this vendor");
        }

        user.setRole(dto.role());
        staffProfileService.hireStaff(user,vendor);

        var savedUser = userRepository.save(user);

        return userMapper.toUserResponseDto(savedUser);
    }

    public UserResponseDto updateUser(UserUpdateDto dto, User authenticatedUser) {
        User user = userRepository.findById(authenticatedUser.getUserId())
                .orElseThrow(() -> new ResourceNotFound("User not found"));

        if(dto.firstName() != null ){
            user.setFirstName(dto.firstName());
        }

        if(dto.lastName() != null ){
            user.setLastName(dto.lastName());
        }

        if(dto.phoneNumber() !=null){
            user.setPhoneNumber(dto.phoneNumber());
        }

        var savedUser = userRepository.save(user);

        return userMapper.toUserResponseDto(savedUser);

    }

    public void changeRole(Long userId,  User authenticatedUser, Role role) {
        if(!authenticatedUser.getRole().equals(Role.OWNER)){
            throw new BusinessRuleException("Invalid Role");
        }

        User user = userRepository.findById(userId)
                .orElseThrow(()->new ResourceNotFound("User wasn't found"));

        if(!user.getVendor().equals(authenticatedUser.getVendor())){
            throw new BusinessRuleException("You cannot perform operations on this user");
        }

        if(role == Role.MANAGER && user.getRole().equals(Role.STAFF)){
            user.setRole(Role.MANAGER);
        } else if(role == Role.STAFF && user.getRole().equals(Role.MANAGER)){
            user.setRole(Role.STAFF);
        }else if(role == Role.STAFF && user.getRole().equals(Role.OWNER)){
            throw new BusinessRuleException("You cannot change this user's role");

        } else {
            throw new BusinessRuleException("Invalid Role");
        }

        userRepository.save(user);
    }


    public List<UserResponseDto> allUsers(User authenticatedUser){
        if(!authenticatedUser.getRole().equals(Role.ADMIN)){
            throw new BusinessRuleException("Sorry you cannot get this because you're ");
        }

        return userRepository.findAll()
                .stream()
                .map(userMapper :: toUserResponseDto)
                .toList();
    }

    public UserResponseDto getUser(User authenticatedUser){
        User user = userRepository.findById(authenticatedUser.getUserId())
                .orElseThrow(() -> new ResourceNotFound("User wasn't found"));

        return userMapper.toUserResponseDto(user);
    }

    public UserResponseDto getUserById(Long userId, User authenticatedUser){
        boolean isUserIsStaff = authenticatedUser.getRole().equals(Role.STAFF);

        if(isUserIsStaff){
            throw new BusinessRuleException("Sorry you cannot see the user");
        }

        User user = userRepository.findById(userId)
                .orElseThrow(() -> new ResourceNotFound("User wasn't found"));

        return userMapper.toUserResponseDto(user);
    }
}
