package com.landconnect.repository;

import com.landconnect.entity.Role;
import com.landconnect.enums.RoleType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface RoleRepository extends JpaRepository<Role,Long> {
    Optional<Role> findByName( RoleType name);
}
