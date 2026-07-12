package com.landconnect.service.impl;

import com.landconnect.dto.request.LandRequest;
import com.landconnect.dto.request.LandSearchRequest;
import com.landconnect.dto.response.ApiResponse;
import com.landconnect.dto.response.LandResponse;
import com.landconnect.entity.Land;
import com.landconnect.entity.User;
import com.landconnect.exception.AccessDeniedException;
import com.landconnect.exception.LandNotFoundException;
import com.landconnect.repository.LandRepository;
import com.landconnect.repository.UserRepository;
import com.landconnect.service.LandService;
import com.landconnect.specification.LandSpecification;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class LandServiceImpl implements LandService {

    private final LandRepository landRepository;
    private final UserRepository userRepository;

    @Override
    public ApiResponse createLand(LandRequest request) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        User user = (User) authentication.getPrincipal();
        Land land = Land.builder()
                .title(request.getTitle())
                .description(request.getDescription())
                .price(request.getPrice())
                .area(request.getArea())
                .state(request.getState())
                .district(request.getDistrict())
                .village(request.getVillage())
                .landType(request.getLandType())
                .owner(user)
                .build();
        Land savedLand = landRepository.save(land);
        LandResponse response = LandResponse.builder()
                .id(savedLand.getId())
                .title(savedLand.getTitle())
                .description(savedLand.getDescription())
                .price(savedLand.getPrice())
                .area(savedLand.getArea())
                .state(savedLand.getState())
                .district(savedLand.getDistrict())
                .village(savedLand.getVillage())
                .landType(savedLand.getLandType())
                .ownerId(user.getId())
                .ownerName(user.getFirstName() + " " + user.getLastName())
                .createdAt(savedLand.getCreatedAt())
                .updatedAt(savedLand.getUpdatedAt())
                .build();


        return ApiResponse.builder()
                .success(true)
                .data(response)
                .message("Land Created Successfully")
                .timestamp(LocalDateTime.now()).build();
    }

    @Override
    public ApiResponse getAllLands() {
        List<Land> lands = landRepository.findAll();
        List<LandResponse> responseList = lands.stream()
                .map(land -> LandResponse.builder()
                        .id(land.getId())
                        .title(land.getTitle())
                        .description(land.getDescription())
                        .price(land.getPrice())
                        .area(land.getArea())
                        .state(land.getState())
                        .district(land.getDistrict())
                        .village(land.getVillage())
                        .landType(land.getLandType())
                        .ownerId(land.getOwner().getId())
                        .ownerName(
                                land.getOwner().getFirstName() + " " + land.getOwner().getLastName())
                        .createdAt(land.getCreatedAt())
                        .updatedAt(land.getUpdatedAt())
                        .build()).toList();
        return ApiResponse.builder()
                .success(true)
                .message("All lands fetched successfully .")
                .data(responseList)
                .timestamp(LocalDateTime.now()).build();
    }

    @Override
    public ApiResponse getLandById(Long id) {
        Land land = landRepository.findById(id)
                .orElseThrow(() ->
                        new LandNotFoundException("Land Not found With Id :" + id));
        LandResponse response = LandResponse.builder()
                .id(land.getId())
                .title(land.getTitle())
                .description(land.getDescription())
                .price(land.getPrice())
                .area(land.getArea())
                .state(land.getState())
                .district(land.getDistrict())
                .village(land.getVillage())
                .landType(land.getLandType())
                .ownerId(land.getOwner().getId())
                .ownerName(
                        land.getOwner().getFirstName() + " " +
                                land.getOwner().getLastName())
                .createdAt(land.getCreatedAt())
                .updatedAt(land.getUpdatedAt())
                .build();
        return ApiResponse.builder()
                .success(true)
                .message("Land fetched successfully")
                .data(response)
                .timestamp(LocalDateTime.now()).build();
    }

    @Override
    public ApiResponse updateLand(Long id, LandRequest request) {
        Land land = landRepository.findById(id)
                .orElseThrow(() ->
                        new LandNotFoundException("Land not found with id:" + id));
        validateOwnership(land);
        land.setTitle(request.getTitle());
        land.setDescription(request.getDescription());
        land.setPrice(request.getPrice());
        land.setArea(request.getArea());
        land.setState(request.getState());
        land.setDistrict(request.getDistrict());
        land.setVillage(request.getVillage());
        land.setLandType(request.getLandType());
        Land updatedLand = landRepository.save(land);

        LandResponse response = LandResponse.builder()
                .id(updatedLand.getId())
                .title(updatedLand.getTitle())
                .description(updatedLand.getDescription())
                .price(updatedLand.getPrice())
                .area(updatedLand.getArea())
                .state(updatedLand.getState())
                .district(updatedLand.getDistrict())
                .village(updatedLand.getVillage())
                .landType(updatedLand.getLandType())
                .ownerId(updatedLand.getOwner().getId())
                .ownerName(updatedLand.getOwner().getFirstName() + " "
                        + updatedLand.getOwner().getLastName())
                .createdAt(updatedLand.getCreatedAt())
                .updatedAt(updatedLand.getUpdatedAt())
                .build();
        return ApiResponse.builder()
                .success(true)
                .message("land updated successfully")
                .data(response)
                .timestamp(LocalDateTime.now()).build();
    }

    @Override
    public ApiResponse deleteLand(Long id) {
        Land land = landRepository.findById(id)
                .orElseThrow(() -> new LandNotFoundException("Land Not Foun With Id:" + id));
        validateOwnership(land);
        landRepository.delete(land);
        return ApiResponse.builder()
                .success(true)
                .message("Land deleted successfully.")
                .data(null)
                .timestamp(LocalDateTime.now()).build();

    }

    @Override
    public ApiResponse getAllLand(int page, int size, String sortBy, String sortDir) {
        Sort sort = sortDir.equalsIgnoreCase("asc")
                ? Sort.by(sortBy).ascending()
                : Sort.by(sortBy).descending();
        Pageable pageable = PageRequest.of(page, size, sort);
        Page<Land> landPage = landRepository.findAll(pageable);
        List<LandResponse> response = landPage.getContent()
                .stream()
                .map(this::mapToResponse)
                .toList();
        return ApiResponse.builder()
                .success(true)
                .message("Land Fetched Successfully")
                .timestamp(LocalDateTime.now()).build();
    }

    @Override
    public ApiResponse searchLands(LandSearchRequest request, int page, int size, String sortBy, String sortDir) {
       Sort sort=sortDir.equalsIgnoreCase("asc")
               ? Sort.by(sortBy).ascending()
               : Sort.by(sortBy).descending();
       Pageable pageable=PageRequest.of(page,size,sort);
        Specification<Land> specification= LandSpecification.searchLand(request);
        Page<Land> landPage=landRepository.findAll(specification,pageable);

        List<LandResponse> response=landPage.getContent()
                .stream().map(this::mapToResponse).toList();
        Map<String,Object> data =new HashMap<>();
        data.put("content",response);

        data.put("currentPage", landPage.getNumber());
        data.put("pageSize", landPage.getSize());
        data.put("totalElements", landPage.getTotalElements());
        data.put("totalPages", landPage.getTotalPages());
        data.put("last", landPage.isLast());

        return ApiResponse.builder()
                .success(true)
                .message("Lands fetched successfully.")
                .data(data)
                .timestamp(LocalDateTime.now())
                .build();



    }

    private LandResponse mapToResponse(Land land) {
        return LandResponse.builder()
                .id(land.getId())
                .title(land.getTitle())
                .description(land.getDescription())
                .price(land.getPrice())
                .area(land.getArea())
                .state(land.getState())
                .district(land.getDistrict())
                .village(land.getVillage())
                .landType(land.getLandType())
                .ownerId(land.getOwner().getId())
                .ownerName(
                        land.getOwner().getFirstName()
                                + " "
                                + land.getOwner().getLastName())
                .createdAt(land.getCreatedAt())
                .updatedAt(land.getUpdatedAt())
                .build();
    }
    private User getCurrentUser(){
        Authentication authentication =
                SecurityContextHolder.getContext().getAuthentication();
    return (User) authentication.getPrincipal();
    }
    private void validateOwnership(Land land) {

        User currentUser = getCurrentUser();

        boolean isAdmin = currentUser.getAuthorities()
                .stream()
                .anyMatch(authority ->
                        authority.getAuthority().equals("ADMIN"));

        if (isAdmin) {
            return;
        }

        if (!land.getOwner().getId().equals(currentUser.getId())) {
            throw new AccessDeniedException(
                    "You are not allowed to modify this land."
            );
        }
    }
}

