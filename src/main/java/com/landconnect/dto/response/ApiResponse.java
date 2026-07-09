package com.landconnect.dto.response;

import lombok.*;

import java.time.LocalDateTime;
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder

public class ApiResponse {
    private boolean success; // true ? success :  login faield
    private String message;
    private Object data ;  // why used object ?  because different time get diffent type of  data that's why
    private LocalDateTime timestamp;
}
