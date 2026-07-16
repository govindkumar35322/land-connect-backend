package com.landconnect.dto.response;

import com.landconnect.enums.BookingStatus;
import lombok.Builder;
import lombok.Data;

import java.time.LocalDate;

@Data
@Builder
public class BookingResponse {
    private Long bookingId;

    private Long landId;

    private String landTitle;

    private String ownerName;

    private LocalDate visitDate;

    private String message;

    private BookingStatus status;
}
