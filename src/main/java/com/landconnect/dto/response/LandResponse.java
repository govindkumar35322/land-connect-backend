package com.landconnect.dto.response;

import com.landconnect.entity.LandType;
import com.landconnect.entity.User;
import jakarta.persistence.Entity;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class LandResponse {
    private Long id;

    private String title;

    private String description;

    private double price;

    private Double area;

    private String state;

    private String district;

    private String village;

    private LandType landType;

    private Long ownerId;

    private String ownerName;

    private LocalDateTime createdAt;

    private LocalDateTime updatedAt;


}
