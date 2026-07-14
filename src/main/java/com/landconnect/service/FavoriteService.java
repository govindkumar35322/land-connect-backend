package com.landconnect.service;

import com.landconnect.dto.response.ApiResponse;
import com.landconnect.dto.response.FavoriteResponse;

import java.util.List;

public interface FavoriteService {
    ApiResponse addFavorite(Long landId);
    ApiResponse removeFavorite(Long landId);
    List<FavoriteResponse> getMyFavorites();
}
