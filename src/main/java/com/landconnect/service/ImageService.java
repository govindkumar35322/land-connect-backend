package com.landconnect.service;

import com.landconnect.dto.response.ApiResponse;
import com.landconnect.dto.response.ImageResponse;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface ImageService {
    ApiResponse uploadImage(Long landId, MultipartFile file);
    ApiResponse deleteImage(Long imageId);
    List<ImageResponse> getImages(Long landId);
    ApiResponse uploadMultipleImages(Long landId, List<MultipartFile> files);
    ApiResponse setCoverImage(Long imageId);
}
