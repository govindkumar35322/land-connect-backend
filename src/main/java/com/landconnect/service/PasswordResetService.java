package com.landconnect.service;

import com.landconnect.dto.request.ForgetPasswordRequest;
import com.landconnect.dto.request.ResetPasswordRequest;
import com.landconnect.dto.response.ApiResponse;
import com.landconnect.entity.PasswordResetToken;
import com.landconnect.entity.User;

import java.util.Optional;

public interface PasswordResetService {


  ApiResponse forgetPassword(ForgetPasswordRequest request);
  ApiResponse resetPassword(ResetPasswordRequest request);

}
