package com.landconnect.dto.request;

import jakarta.validation.constraints.Future;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.time.LocalDate;

@Data
public class BookingRequest {
    @NotNull(message = "Land ID is required")
    private Long landId;

    @NotNull(message = "Visit date is required")
    @Future(message = "Visit date must be in the future")
    private LocalDate visitDate;

    @NotBlank(message = "Message is required")
    private String message;
}
