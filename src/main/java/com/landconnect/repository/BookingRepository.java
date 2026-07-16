package com.landconnect.repository;

import com.landconnect.entity.Booking;
import com.landconnect.entity.Land;
import com.landconnect.entity.User;
import com.landconnect.enums.BookingStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface BookingRepository  extends JpaRepository<Booking,Long> {
    // Get all bookings of a user
    List<Booking> findByUser(User user);

    // Get all bookings of a land
    List<Booking> findByLand(Land land);

    // Get bookings by status
    List<Booking> findByStatus(BookingStatus status);

    // Check duplicate booking
    Optional<Booking> findByUserAndLand(User user, Land land);

    // Get bookings of a user with a specific status
    List<Booking> findByUserAndStatus(User user, BookingStatus status);

    // Get bookings of a land with a specific status
    List<Booking> findByLandAndStatus(Land land, BookingStatus status);
}
