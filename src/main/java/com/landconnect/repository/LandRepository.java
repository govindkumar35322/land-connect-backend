package com.landconnect.repository;

import com.landconnect.entity.Land;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

@Repository
public interface LandRepository  extends JpaRepository<Land, Long>, JpaSpecificationExecutor<Land> {

}
