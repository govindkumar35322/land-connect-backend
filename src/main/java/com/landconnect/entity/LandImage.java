package com.landconnect.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder
@Table(name="land_images")
public class LandImage  extends BaseEntity {
    @Id
    @GeneratedValue(strategy  =GenerationType.IDENTITY)
    private Long id;
    @Column(nullable=false)
    private String imageName;
    @Column(nullable = false)
    private  String imageUrl;
    @Column(nullable = false)
    private Boolean coverImage;
    @ManyToOne(fetch=FetchType.LAZY)
    @JoinColumn(name="land_id",nullable=false)
    private Land land;
}
