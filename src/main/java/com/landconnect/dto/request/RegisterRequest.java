package com.landconnect.dto.request;

import com.landconnect.enums.RoleType;
import jakarta.validation.constraints.*;
import lombok.*;

import java.util.Set;
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class RegisterRequest {
    @NotNull
    @NotBlank(message = "First name is required")
    @Size(min=2,max=50 ,message="First name be between 2 and 50 characters")
    private String firstName;

    @NotBlank(message = "Last name is required")
    @Size(min=2,max=50 ,message="Last name be between 2 and 50 characters")
    private String lastName;
    @Email(message="invalid email format")
    @NotBlank(message="Email is required")
    private String email;
    @NotBlank
   @Pattern(regexp="^[6-9]\\d{9}$",message="phone number must be a valid 10 digit ")
    private String phoneNumber;

    @NotBlank(message="Password is required")
    @Size(min=6, message="Password must be at least 6 characters long")
    private String password;
    @NotBlank(message="Confirm password is required")
    private String confirmPassword;
    @NotEmpty(message="At least one role must be selected")
    private Set<RoleType> roles;
}
