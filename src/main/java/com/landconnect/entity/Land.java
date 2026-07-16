package com.landconnect.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Array;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Table(name="lands")
public class Land  extends BaseEntity{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(nullable=false)
    private String title;
    @Column(length = 1000)
    private String description;
    @Column(nullable = false)
    private double price;
    @Column(nullable = false)
    private String state;
    @Column(nullable = false)
    private  Double area;
    @Column(nullable = false)
    private String district;
    @ManyToOne(fetch=FetchType.LAZY)
    @JoinColumn(name="owner_id",nullable = false)
    private User owner;
    @Column(nullable = false)
    private String village;
    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private LandType landType;

    @OneToMany(mappedBy = "land", cascade = CascadeType.ALL)
    private List<Favorite> favorites = new ArrayList<>();

    @OneToMany(mappedBy = "land",cascade=CascadeType.ALL,orphanRemoval=true)
    private List<LandImage> image=new ArrayList<>();

    @JsonIgnore
    @OneToMany(mappedBy = "land",cascade = CascadeType.ALL,orphanRemoval = true)
    private List<Booking> bookings=new ArrayList<>();
}
