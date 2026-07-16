package com.landconnect.service;


import com.landconnect.dto.request.BookingRequest;
import com.landconnect.dto.response.ApiResponse;
import com.landconnect.dto.response.BookingResponse;

import java.util.List;

public interface BookingService {
    // User creates a booking request
    ApiResponse createBooking(BookingRequest request);

    // Logged-in user views their bookings
    List<BookingResponse> getMyBookings();

    // Owner views booking requests for a specific land
    List<BookingResponse> getBookingsForLand(Long landId);

    // Owner approves a booking
    ApiResponse approveBooking(Long bookingId);

    // Owner rejects a booking
    ApiResponse rejectBooking(Long bookingId);

    // User cancels their booking
    ApiResponse cancelBooking(Long bookingId);
}