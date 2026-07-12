package com.landconnect.dto.request;

import com.landconnect.entity.LandType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class LandSearchRequest {
    private String state;
    private String district;
    private String village;

    private LandType landType;

    private double minPrice;
    private double maxPrice;

    private double minArea;
    private double maxArea;
}
