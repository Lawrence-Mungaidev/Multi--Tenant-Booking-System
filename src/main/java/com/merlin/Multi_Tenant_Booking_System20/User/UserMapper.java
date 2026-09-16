package com.merlin.Multi_Tenant_Booking_System20.User;

import org.springframework.stereotype.Component;

@Component
public class UserMapper {

    public User toUser (UserDto dto){
        User user = new User();
        user.setFirstName(dto.firstName());
        user.setLastName(dto.lastName());
        user.setEmail(dto.email());

        return user;
    }

    public UserResponseDto toUserResponseDto (User user){
        return new UserResponseDto(user.getFirstName(), user.getLastName(), user.getRole(),user.getVendor().getVendorId());
    }
}
