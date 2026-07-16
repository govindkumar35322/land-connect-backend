package com.landconnect.service.impl;

import com.landconnect.dto.response.ApiResponse;
import com.landconnect.dto.response.ImageResponse;
import com.landconnect.entity.Land;
import com.landconnect.entity.LandImage;
import com.landconnect.exception.ResourceNotFoundException;
import com.landconnect.repository.LandImageRepository;
import com.landconnect.repository.LandRepository;
import com.landconnect.service.ImageService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.List;
import java.util.UUID;

@RequiredArgsConstructor
@Service
public class ImageServiceImpl  implements ImageService {
private final LandRepository landRepository;
 private final LandImageRepository imageRepository;
 private static final String UPLOAD_DIR="uploads/";

    @Override
    public ApiResponse uploadImage(Long landId, MultipartFile file) {
        try{
            Land land=landRepository.findById(landId)
                    .orElseThrow( ()-> new ResourceNotFoundException(
                            "Land not found with id: "+ landId));
                    String fileName=saveFile(file);

            LandImage image= LandImage.builder()
                    .imageName(fileName)
                    .imageUrl("/uploads/"+ fileName)
                    .coverImage(false)
                    .land(land).build();

            imageRepository.save(image);
            return ApiResponse.builder()
                    .success(true)
                    .message("Image uploaded successfully.")
                    .data(image.getImageUrl()).build();
        }
        catch(IOException e){
            throw  new RuntimeException("Failed to upload image.", e);
        }

    }

    @Override
    public ApiResponse deleteImage(Long imageId) {

        LandImage image = imageRepository.findById(imageId)
                .orElseThrow(() ->
                        new ResourceNotFoundException(
                                "Image not found with id: " + imageId));

        try {

            Path filePath = Paths.get(UPLOAD_DIR)
                    .resolve(image.getImageName());

            Files.deleteIfExists(filePath);

        } catch (IOException e) {
            throw new RuntimeException("Unable to delete image file.", e);
        }

        imageRepository.delete(image);

        return ApiResponse.builder()
                .success(true)
                .message("Image deleted successfully.")
                .data(null)
                .build();
    }

    @Override
    public List<ImageResponse> getImages(Long landId) {
        Land land=landRepository.findById(landId)
                .orElseThrow(()-> new ResourceNotFoundException("Land not found with id:"+ landId));
      List<LandImage> images=imageRepository.findByLand(land);

        return images.stream()
                .map(image->ImageResponse.builder()
                        .id(image.getId())
                        .imageName(image.getImageName())
                        .imageUrl(image.getImageUrl())
                        .coverImage(image.getCoverImage()).build()).toList();
    }

    @Override
    public ApiResponse uploadMultipleImages(Long landId, List<MultipartFile> files) {
        for(MultipartFile file :files){
            uploadImage(landId,file);
        }

        return ApiResponse.builder()
                .success(true)
                .message("All images uploaded successfully .")
                .data(null).build() ;
    }

    @Override
    public ApiResponse setCoverImage(Long imageId) {
        LandImage selectedImage = imageRepository.findById(imageId)
                .orElseThrow(() ->
                        new ResourceNotFoundException(
                                "Image not found with id: " + imageId));

        List<LandImage> images =
                imageRepository.findByLand(selectedImage.getLand());

        for (LandImage image : images) {
            image.setCoverImage(false);
        }

        selectedImage.setCoverImage(true);

        imageRepository.saveAll(images);

        return ApiResponse.builder()
                .success(true)
                .message("Cover image updated successfully.")
                .data(null)
                .build();
    }



    private String saveFile(MultipartFile file) throws IOException {

        Path uploadPath = Paths.get(UPLOAD_DIR);

        if (!Files.exists(uploadPath)) {
            Files.createDirectories(uploadPath);
        }

        String fileName =
                UUID.randomUUID() + "_" + file.getOriginalFilename();

        Path filePath = uploadPath.resolve(fileName);

        Files.copy(
                file.getInputStream(),
                filePath,
                StandardCopyOption.REPLACE_EXISTING);

        return fileName;
    }
}
