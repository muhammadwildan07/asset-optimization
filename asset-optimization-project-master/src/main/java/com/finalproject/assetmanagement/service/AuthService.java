package com.finalproject.assetmanagement.service;

import com.finalproject.assetmanagement.model.request.AuthRequest;
import com.finalproject.assetmanagement.model.response.LoginResponse;
import com.finalproject.assetmanagement.model.response.RegisterResponse;

public interface AuthService {
    RegisterResponse registerEmployee(AuthRequest request);
    RegisterResponse registerManager(AuthRequest request);
    LoginResponse login(AuthRequest request);



}

