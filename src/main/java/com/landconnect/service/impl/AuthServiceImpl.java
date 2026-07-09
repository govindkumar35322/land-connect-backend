package com.landconnect.service.impl;

import com.landconnect.dto.request.LoginRequest;
import com.landconnect.dto.request.RegisterRequest;
import com.landconnect.dto.response.ApiResponse;
import com.landconnect.entity.Role;
import com.landconnect.entity.User;
import com.landconnect.repository.RoleRepository;
import com.landconnect.repository.UserRepository;
import com.landconnect.service.AuthService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Set;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class AuthServiceImpl implements AuthService {
    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final PasswordEncoder passwordEncoder;
    @Override
    public ApiResponse register(RegisterRequest request) {
       if(userRepository.existsByEmail(request.getEmail())){
           return ApiResponse.builder()
                   .success(false)
                   .message("Email Already Registered")
                   .build();
       }
       if(userRepository.existsByPhoneNumber(request.getPhoneNumber())){
           return ApiResponse.builder()
                   .success(false)
                   .message("Phone Number Already Registered")
                   .build();
       }
       if(!request.getPassword().equals(request.getConfirmPassword()))
       {
           return ApiResponse.builder()
                   .success(false)
                   .message("Password and confirm password do not Match.")
                   .build();
       }
       Set<Role> roles= request.getRoles()
               .stream()
               .map(roleType->roleRepository.findByName(roleType)
                       .orElseThrow(()-> new RuntimeException("Role Not Found"+ roleType)))
               .collect(Collectors.toSet());
        // create User object here
       User user = User.builder()
               .firstName(request.getFirstName())
               .lastName(request.getLastName())
               .email(request.getEmail())
               .phoneNumber(request.getPhoneNumber())
               .enabled(true)
               .roles(roles)
               .build();
     user.setPassword(passwordEncoder.encode(request.getPassword()));
     userRepository.save(user);
       return ApiResponse.builder()
               .success(true)
               .message("Registered Successfully.")
               .data(null)
               .timestamp(java.time.LocalDateTime.now())
               .build();
    }



    @Override
    public ApiResponse login(LoginRequest request) {
        return null;
    }
}
