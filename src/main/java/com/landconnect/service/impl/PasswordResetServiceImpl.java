package com.landconnect.service.impl;

import com.landconnect.dto.request.ForgetPasswordRequest;
import com.landconnect.dto.request.ResetPasswordRequest;
import com.landconnect.dto.response.ApiResponse;
import com.landconnect.entity.PasswordResetToken;
import com.landconnect.entity.User;
import com.landconnect.exception.ResourceNotFoundException;
import com.landconnect.repository.PasswordResetTokenRepository;
import com.landconnect.repository.UserRepository;
import com.landconnect.service.EmailTemplateService;
import com.landconnect.service.MailService;
import com.landconnect.service.PasswordResetService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class PasswordResetServiceImpl implements PasswordResetService {
    private final UserRepository userRepository;
    private final PasswordResetTokenRepository passwordResetTokenRepository;
    private final PasswordEncoder passwordEncoder;
    private final MailService mailService;
    private final EmailTemplateService emailTemplateService;
    @Override
    public ApiResponse forgetPassword(ForgetPasswordRequest request) {
       User user=userRepository.findByEmail(request.getEmail())
               .orElseThrow(()-> new ResourceNotFoundException("User not found with email:"+ request.getEmail()));
       passwordResetTokenRepository.deleteByUser(user);
       String token= java.util.UUID.randomUUID().toString();
        PasswordResetToken resetToken = PasswordResetToken.builder()
                .token(token)
                .user(user)
                .expiryDate(java.time.LocalDateTime.now().plusMinutes(15))
                .build();

        passwordResetTokenRepository.save(resetToken);
        String resetLink =
                "http://localhost:3000/reset-password?token=" + token;

        String html = emailTemplateService.getPasswordResetTemplate(
                user.getFirstName(),
                resetLink
        );

        mailService.sendHtmlEmail(
                user.getEmail(),
                "Reset Your Password",
                html
        );

        return  ApiResponse.builder()
                .success(true).data(null).message("Password reset email sent successfully.").build();
    }

    @Override
    public ApiResponse resetPassword(ResetPasswordRequest request) {
        PasswordResetToken resetToken = passwordResetTokenRepository
                .findByToken(request.getToken())
                .orElseThrow(() ->
                        new ResourceNotFoundException("Invalid reset token."));
        if (resetToken.getExpiryDate().isBefore(java.time.LocalDateTime.now())) {
            throw new IllegalStateException("Reset token has expired.");
        }
        if (!request.getNewPassword().equals(request.getConfirmPassword())) {
            throw new IllegalStateException(
                    "Password and Confirm Password do not match.");
        }
        User user = resetToken.getUser();
        user.setPassword(
                passwordEncoder.encode(request.getNewPassword())
        );
        userRepository.save(user);
        passwordResetTokenRepository.delete(resetToken);
        return ApiResponse.builder()
                .success(true)
                .message("Password reset successfully.")
                .data(null).build();
    }
}
