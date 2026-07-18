package com.landconnect.service.impl;

import com.landconnect.dto.request.LoginRequest;
import com.landconnect.dto.request.RegisterRequest;
import com.landconnect.dto.response.ApiResponse;
import com.landconnect.dto.response.LoginResponse;
import com.landconnect.entity.Role;
import com.landconnect.entity.User;
import com.landconnect.exception.EmailAlreadyExistsException;
import com.landconnect.exception.PasswordMismatchException;
import com.landconnect.exception.PhoneNumberAlreadyExistsException;
import com.landconnect.exception.RoleNotFoundException;
import com.landconnect.repository.RoleRepository;
import com.landconnect.repository.UserRepository;
import com.landconnect.security.jwt.JwtService;
import com.landconnect.service.AuthService;
import com.landconnect.service.EmailTemplateService;
import com.landconnect.service.MailService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.security.core.Authentication;
import java.time.LocalDateTime;
import java.util.Set;
import java.util.stream.Collectors;



@Service
@RequiredArgsConstructor
public class AuthServiceImpl implements AuthService {
    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final PasswordEncoder passwordEncoder;
    private final AuthenticationManager authenticationManager;
    private final JwtService jwtService;
    private final MailService mailService;
    private final EmailTemplateService emailTemplateService;

    @Override
    public ApiResponse register(RegisterRequest request) {

        if (userRepository.existsByEmail(request.getEmail())) {
            throw new EmailAlreadyExistsException("Email already registered.");
        }


        if (userRepository.existsByPhoneNumber(request.getPhoneNumber())) {
            throw new PhoneNumberAlreadyExistsException("Phone number already registered.");
        }


        if (!request.getPassword().equals(request.getConfirmPassword())) {
            throw new PasswordMismatchException("Password and Confirm Password do not match.");
        }

       Set<Role> roles= request.getRoles()
               .stream()
               .map(roleType->roleRepository.findByName(roleType)
                       .orElseThrow(()-> new RoleNotFoundException("Role Not Found: "+ roleType)))
                                             // RunTimeException
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
      User savedUser= userRepository.save(user);
        String html = emailTemplateService.getWelcomeTemplate(
                savedUser.getFirstName()
        );
        mailService.sendHtmlEmail(
                savedUser.getEmail(),
                "Welcome to Land Connect",
                html
        );
       return ApiResponse.builder()
               .success(true)
               .message("Registered Successfully.")
               .data(null)
               .timestamp(LocalDateTime.now())
               .build();
    }
    @Override
    public ApiResponse login(LoginRequest request) {

        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        request.getEmail(),
                        request.getPassword()
                )
        );

        User user = (User) authentication.getPrincipal();

        String token = jwtService.generateToken(user);

        LoginResponse loginResponse = LoginResponse.builder()
                .token(token)
                .tokenType("Bearer")
                .build();

        return ApiResponse.builder()
                .success(true)
                .message("Login Successfully.")
                .data(loginResponse)
                .timestamp(LocalDateTime.now())
                .build();
    }



}
