package com.landconnect.repository;

import com.landconnect.entity.Favorite;
import com.landconnect.entity.Land;
import com.landconnect.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface FavoriteRepository  extends JpaRepository<Favorite,Long> {
    boolean existsByUserAndLand(User user, Land land);

    Optional<Favorite> findByUserAndLand(User user, Land land);

    List<Favorite> findByUser(User user);
}
