package com.landconnect.entity;

import com.landconnect.enums.BookingStatus;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Table(name="bookings")
public class Booking  extends BaseEntity{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    // Buyer
    @ManyToOne(fetch=FetchType.LAZY)
    @JoinColumn(name="user_id",nullable=false)
    private User user;
    // Land
    @ManyToOne(fetch=FetchType.LAZY)
    @JoinColumn(name="land_id",nullable=false)
    private Land land;
    @Column(nullable = false)
    private LocalDate visitDate;
    @Column(length=1000)
    private String message;
    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private BookingStatus status;
}
