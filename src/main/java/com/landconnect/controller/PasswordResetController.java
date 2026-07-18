package com.landconnect.controller;

import com.landconnect.dto.request.ForgetPasswordRequest;
import com.landconnect.dto.request.ResetPasswordRequest;
import com.landconnect.dto.response.ApiResponse;
import com.landconnect.service.PasswordResetService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/auth")
public class PasswordResetController {
    private final PasswordResetService passwordResetService;

    @PostMapping("/forgot-password")
    public ResponseEntity<ApiResponse> forgotPassword(
            @Valid @RequestBody ForgetPasswordRequest request) {

        return ResponseEntity.ok(
                passwordResetService.forgetPassword(request)
        );
    }

    @PostMapping("/reset-password")
    public ResponseEntity<ApiResponse> resetPassword(
            @Valid @RequestBody ResetPasswordRequest request) {

        return ResponseEntity.ok(
                passwordResetService.resetPassword(request)
        );
    }
}