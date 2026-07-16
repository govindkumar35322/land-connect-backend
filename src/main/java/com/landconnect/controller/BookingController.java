package com.landconnect.controller;

import com.landconnect.dto.request.BookingRequest;
import com.landconnect.dto.response.ApiResponse;
import com.landconnect.dto.response.BookingResponse;
import com.landconnect.service.BookingService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/v1/bookings")
public class BookingController {
    private final BookingService bookingService;
    @PostMapping
    public ResponseEntity<ApiResponse> createBooking(
            @Valid @RequestBody BookingRequest request) {

        return ResponseEntity.ok(
                bookingService.createBooking(request));
    }
    @GetMapping("/my")
    public ResponseEntity<List<BookingResponse>> getMyBookings() {

        return ResponseEntity.ok(
                bookingService.getMyBookings());
    }
    @GetMapping("/land/{landId}")
    public ResponseEntity<List<BookingResponse>> getBookingsForLand(
            @PathVariable Long landId) {

        return ResponseEntity.ok(
                bookingService.getBookingsForLand(landId));
    }
    @PutMapping("/{bookingId}/approve")
    public ResponseEntity<ApiResponse> approveBooking(
            @PathVariable Long bookingId) {

        return ResponseEntity.ok(
                bookingService.approveBooking(bookingId));
    }
    @PutMapping("/{bookingId}/reject")
    public ResponseEntity<ApiResponse> rejectBooking(
            @PathVariable Long bookingId) {

        return ResponseEntity.ok(
                bookingService.rejectBooking(bookingId));
    }
    @PutMapping("/{bookingId}/cancel")
    public ResponseEntity<ApiResponse> cancelBooking(
            @PathVariable Long bookingId) {

        return ResponseEntity.ok(
                bookingService.cancelBooking(bookingId));
    }
}
