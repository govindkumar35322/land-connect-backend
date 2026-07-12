package com.landconnect.service;

import com.landconnect.dto.request.LandRequest;
import com.landconnect.dto.request.LandSearchRequest;
import com.landconnect.dto.response.ApiResponse;

public interface LandService {
      ApiResponse createLand(LandRequest request);
       ApiResponse getAllLands();
       ApiResponse getLandById(Long id);
       ApiResponse updateLand(Long id ,LandRequest request);
       ApiResponse deleteLand(Long id);
       ApiResponse getAllLand(int page,int size,String sortBy,String sortDir);
       ApiResponse searchLands(LandSearchRequest request, int page, int size, String sortBy, String sortDir);




}
