package com.merlin.Multi_Tenant_Booking_System20.Auth;

import com.merlin.Multi_Tenant_Booking_System20.User.UserMapper;
import com.merlin.Multi_Tenant_Booking_System20.User.UserRepository;
import com.merlin.Multi_Tenant_Booking_System20.User.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final UserMapper userMapper;

    p

}
