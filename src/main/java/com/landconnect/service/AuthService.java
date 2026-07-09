package com.landconnect.service;

import com.landconnect.dto.request.LoginRequest;
import com.landconnect.dto.request.RegisterRequest;
import com.landconnect.dto.response.ApiResponse;

public interface AuthService {
    ApiResponse register(RegisterRequest request);
    ApiResponse login(LoginRequest request);
}
