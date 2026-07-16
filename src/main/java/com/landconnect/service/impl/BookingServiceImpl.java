package com.landconnect.service.impl;

import com.landconnect.dto.request.BookingRequest;
import com.landconnect.dto.response.ApiResponse;
import com.landconnect.dto.response.BookingResponse;
import com.landconnect.entity.Booking;
import com.landconnect.entity.Land;
import com.landconnect.entity.User;
import com.landconnect.enums.BookingStatus;
import com.landconnect.exception.ResourceNotFoundException;
import com.landconnect.repository.BookingRepository;
import com.landconnect.repository.LandRepository;
import com.landconnect.repository.UserRepository;
import com.landconnect.service.BookingService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.List;
@Service
@RequiredArgsConstructor
public class BookingServiceImpl  implements BookingService {
    private final BookingRepository bookingRepository;
    private final LandRepository landRepository;
    private final UserRepository userRepository;

    private User getCurrentUser() {

        Authentication authentication =
                SecurityContextHolder.getContext().getAuthentication();

        String email = authentication.getName();

        return userRepository.findByEmail(email)
                .orElseThrow(() ->
                        new ResourceNotFoundException("User not found"));
    }

    @Override
    public ApiResponse createBooking(BookingRequest request) {
         User currentUser=getCurrentUser();
         Land land=landRepository.findById(request.getLandId())
                 .orElseThrow(()-> new ResourceNotFoundException("Land not found with id: "+ request.getLandId()));
         bookingRepository.findByUserAndLand(currentUser,land)
                 .ifPresent(booking->{
                     throw  new IllegalStateException("You have already booked this land .");
                 });
         Booking booking= Booking.builder()
                 .user(currentUser)
                 .land(land)
                 .visitDate(request.getVisitDate())
                 .message(request.getMessage())
                 .status(BookingStatus.PENDING).build();
         bookingRepository.save(booking);

        return  ApiResponse.builder()
                .success(true)
                .message("Booking request submitted successfully. ")
                .data(null).build();
    }

    @Override
    public List<BookingResponse> getMyBookings() {
        User currentUser=getCurrentUser();
        List<Booking> bookings=bookingRepository.findByUser(currentUser);
        return bookings.stream()
                .map(this::mapToBookingResponse)
                .toList();


    }
    private BookingResponse mapToBookingResponse(Booking booking) {

        return BookingResponse.builder()
                .bookingId(booking.getId())
                .landId(booking.getLand().getId())
                .landTitle(booking.getLand().getTitle())
                .ownerName(
                        booking.getLand()
                                .getOwner()
                                .getFirstName()
                                + " "
                                + booking.getLand()
                                .getOwner()
                                .getLastName()
                )
                .visitDate(booking.getVisitDate())
                .message(booking.getMessage())
                .status(booking.getStatus())
                .build();
    }

    @Override
    public List<BookingResponse> getBookingsForLand(Long landId) {
        User currentUser = getCurrentUser();

        Land land = landRepository.findById(landId)
                .orElseThrow(() ->
                        new ResourceNotFoundException(
                                "Land not found with id: " + landId));

        // Only the owner can view booking requests
        if (!land.getOwner().getId().equals(currentUser.getId())) {
            throw new IllegalStateException(
                    "You are not authorized to view bookings for this land.");
        }

        List<Booking> bookings = bookingRepository.findByLand(land);

        return bookings.stream()
                .map(this::mapToBookingResponse)
                .toList();
    }

    @Override
    public ApiResponse approveBooking(Long bookingId) {
        User currentUser = getCurrentUser();

        Booking booking = bookingRepository.findById(bookingId)
                .orElseThrow(() ->
                        new ResourceNotFoundException(
                                "Booking not found with id: " + bookingId));

        // Only the land owner can approve the booking
        if (!booking.getLand().getOwner().getId().equals(currentUser.getId())) {
            throw new IllegalStateException(
                    "You are not authorized to approve this booking.");
        }

        // Booking must be in PENDING status
        if (booking.getStatus() != BookingStatus.PENDING) {
            throw new IllegalStateException(
                    "Only pending bookings can be approved.");
        }

        booking.setStatus(BookingStatus.APPROVED);

        bookingRepository.save(booking);
        return ApiResponse.builder()
                .success(true)
                .message("Booking approved successfully.")
                .data(mapToBookingResponse(booking))
                .build();
    }

    @Override
    public ApiResponse rejectBooking(Long bookingId) {
        User currentUser = getCurrentUser();

        Booking booking = bookingRepository.findById(bookingId)
                .orElseThrow(() ->
                        new ResourceNotFoundException(
                                "Booking not found with id: " + bookingId));

        // Only the land owner can reject the booking
        if (!booking.getLand().getOwner().getId().equals(currentUser.getId())) {
            throw new IllegalStateException(
                    "You are not authorized to reject this booking.");
        }

        // Booking must be in PENDING status
        if (booking.getStatus() != BookingStatus.PENDING) {
            throw new IllegalStateException(
                    "Only pending bookings can be rejected.");
        }
        booking.setStatus(BookingStatus.REJECTED);

        bookingRepository.save(booking);

        return ApiResponse.builder()
                .success(true)
                .message("Booking rejected successfully.")
                .data(mapToBookingResponse(booking))
                .build();
    }

    @Override
    public ApiResponse cancelBooking(Long bookingId) {
        User currentUser = getCurrentUser();

        Booking booking = bookingRepository.findById(bookingId)
                .orElseThrow(() ->
                        new ResourceNotFoundException(
                                "Booking not found with id: " + bookingId));

        // Only the buyer who created the booking can cancel it
        if (!booking.getUser().getId().equals(currentUser.getId())) {
            throw new IllegalStateException(
                    "You are not authorized to cancel this booking.");
        }

        // Cannot cancel a completed booking
        if (booking.getStatus() == BookingStatus.COMPLETED) {
            throw new IllegalStateException(
                    "Completed booking cannot be cancelled.");
        }
        // Already cancelled
        if (booking.getStatus() == BookingStatus.CANCELLED) {
            throw new IllegalStateException(
                    "Booking is already cancelled.");
        }

        booking.setStatus(BookingStatus.CANCELLED);

        bookingRepository.save(booking);

        return ApiResponse.builder()
                .success(true)
                .message("Booking cancelled successfully.")
                .data(mapToBookingResponse(booking))
                .build();
    }
    }