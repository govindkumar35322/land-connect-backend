package com.landconnect.controller;

import com.landconnect.dto.response.ApiResponse;
import com.landconnect.dto.response.ImageResponse;
import com.landconnect.service.ImageService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1")
public class ImageController {
    private final ImageService imageService;

    @PostMapping("/lands/{landId}/images")
    public ResponseEntity<ApiResponse> uploadImage(
            @PathVariable  Long landId,
            @RequestParam("file")MultipartFile file
            ){
        return ResponseEntity.ok(imageService.uploadImage(landId,file));

    }
    @PostMapping("/lands/{landId}/images/multiple")
    public ResponseEntity<ApiResponse> uploadMultipleImages(
            @PathVariable Long landId,
            @RequestParam("files") List<MultipartFile> files) {

        return ResponseEntity.ok(
                imageService.uploadMultipleImages(landId, files));
    }

    @GetMapping("/lands/{landId}/images")
    public ResponseEntity<List<ImageResponse>> getImages(
            @PathVariable Long landId) {

        return ResponseEntity.ok(
                imageService.getImages(landId));
    }

    @DeleteMapping("/images/{imageId}")
    public ResponseEntity<ApiResponse> deleteImage(
            @PathVariable Long imageId) {

        return ResponseEntity.ok(
                imageService.deleteImage(imageId));
    }
    @PutMapping("/images/{imageId}/cover")
    public ResponseEntity<ApiResponse> setCoverImage(
            @PathVariable Long imageId) {

        return ResponseEntity.ok(
                imageService.setCoverImage(imageId));
    }

}
