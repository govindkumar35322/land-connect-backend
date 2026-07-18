package com.landconnect.dto.request;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class ResetPasswordRequest {

    @NotBlank(message ="Token is required")
    private String token;
    @NotBlank(message = " New Password is required")
    private String newPassword;
    @NotBlank(message = "Confirm password is required")
    private String confirmPassword;
}
