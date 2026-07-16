package com.landconnect.repository;

import com.landconnect.entity.Land;
import com.landconnect.entity.LandImage;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface LandImageRepository  extends JpaRepository<LandImage,Long> {
List<LandImage> findByLand(Land land);
}
