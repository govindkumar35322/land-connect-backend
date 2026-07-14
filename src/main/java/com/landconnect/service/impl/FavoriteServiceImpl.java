package com.landconnect.service.impl;

import com.landconnect.dto.response.ApiResponse;
import com.landconnect.dto.response.FavoriteResponse;
import com.landconnect.entity.Favorite;
import com.landconnect.entity.Land;
import com.landconnect.entity.User;
import com.landconnect.exception.ResourceNotFoundException;
import com.landconnect.repository.FavoriteRepository;
import com.landconnect.repository.LandRepository;
import com.landconnect.repository.UserRepository;
import com.landconnect.service.FavoriteService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class FavoriteServiceImpl implements FavoriteService {
    private final FavoriteRepository favoriteRepository;
    private final LandRepository landRepository;
    private final UserRepository userRepository;



    @Override
    public ApiResponse addFavorite(Long landId) {
        User currentUser = getCurrentUser();

        Land land = landRepository.findById(landId)
                .orElseThrow(() ->
                        new ResourceNotFoundException(
                                "Land not found with id: " + landId));

        if (favoriteRepository.existsByUserAndLand(currentUser, land)) {
            return ApiResponse.builder()
                    .success(false)
                    .message("Land is already in your favorites.")
                    .data(null)
                    .build();
        }

        Favorite favorite = Favorite.builder()
                .user(currentUser)
                .land(land)
                .build();

        favoriteRepository.save(favorite);
        return ApiResponse.builder()
                .success(true)
                .message("Land added to favorites successfully.")
                .data(null)
                .build();


    }

    @Override
    public ApiResponse removeFavorite(Long landId) {
        User currentUser = getCurrentUser();

        Land land = landRepository.findById(landId)
                .orElseThrow(() ->
                        new ResourceNotFoundException(
                                "Land not found with id: " + landId));

        Favorite favorite = favoriteRepository
                .findByUserAndLand(currentUser, land)
                .orElseThrow(() ->
                        new ResourceNotFoundException(
                                "Favorite not found."));

        favoriteRepository.delete(favorite);

        return ApiResponse.builder()
                .success(true)
                .message("Land removed from favorites successfully.")
                .data(null)
                .build();
    }

    @Override
    public List<FavoriteResponse> getMyFavorites() {
        User currentUser = getCurrentUser();

        List<Favorite> favorites = favoriteRepository.findByUser(currentUser);

        return favorites.stream()
                .map(favorite -> FavoriteResponse.builder()
                        .favoriteId(favorite.getId())
                        .landId(favorite.getLand().getId())
                        .title(favorite.getLand().getTitle())
                        .price(favorite.getLand().getPrice())
                        .state(favorite.getLand().getState())
                        .district(favorite.getLand().getDistrict())
                        .village(favorite.getLand().getVillage())
                        .area(favorite.getLand().getArea())
                        .landType(favorite.getLand().getLandType().name())
                        .ownerName(favorite.getLand().getOwner().getFirstName()
                                + " "
                                + favorite.getLand().getOwner().getLastName())
                        .build())
                .toList();
    }

    private User getCurrentUser() {

        Authentication authentication =
                SecurityContextHolder.getContext().getAuthentication();

        String email = authentication.getName();

        return userRepository.findByEmail(email)
                .orElseThrow(() ->
                        new ResourceNotFoundException("User not found"));
    }
}
