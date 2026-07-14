package com.landconnect.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data@NoArgsConstructor
@AllArgsConstructor
@Builder
public class FavoriteResponse {
 private Long favoriteId;
 private Long landId;
 private String title;
 private Double price;
 private String state;
 private String district;
 private String village;
 private double area;
 private String landType;
 private String ownerName;
}
