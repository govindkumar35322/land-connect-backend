package com.landconnect.controller;

import com.landconnect.dto.response.ApiResponse;
import com.landconnect.dto.response.FavoriteResponse;
import com.landconnect.service.FavoriteService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("api/v1/favorites")
public class FavoriteController {
    private final FavoriteService favoriteService;

    @PostMapping("/{landId}")
    public ResponseEntity<ApiResponse> addFavorite(
            @PathVariable Long landId) {

        return ResponseEntity.ok(
                favoriteService.addFavorite(landId));
    }

    @DeleteMapping("/{landId}")
    public ResponseEntity<ApiResponse> removeFavorite(
            @PathVariable Long landId) {

        return ResponseEntity.ok(
                favoriteService.removeFavorite(landId));
    }
    @GetMapping
    public ResponseEntity<List<FavoriteResponse>> getMyFavorites() {

        return ResponseEntity.ok(
                favoriteService.getMyFavorites());
    }
}
