package com.merlin.Multi_Tenant_Booking_System20.Auth;

import com.merlin.Multi_Tenant_Booking_System20.User.User;
import org.springframework.stereotype.Component;

@Component
public class AuthMapper {

    public User toUser(AuthRegisterDto dto) {
        User user = new User();
        user.setFirstName(dto.firstName());
        user.setLastName(dto.lastName());
        user.setEmail(dto.email());
        user.setRole(dto.role());

        return user;
    }

    public AuthResponse toAuthResponse(User user, String message) {
        return new AuthResponse(user.getEmail(), message );
    }

    public AuthTokenResponse toAuthTokenResponse(User user, String token) {
        return new AuthTokenResponse(user.getRole(),token, user.getFirstName(),user.isMustChangePassword());
    }
}
