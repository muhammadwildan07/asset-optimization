package com.finalproject.assetmanagement.controller;

import com.finalproject.assetmanagement.model.request.AuthRequest;
import com.finalproject.assetmanagement.model.response.CommonResponse;
import com.finalproject.assetmanagement.model.response.LoginResponse;
import com.finalproject.assetmanagement.model.response.RegisterResponse;
import com.finalproject.assetmanagement.service.AuthService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping(path = "/api/v1/auth")
public class AuthController {
    private final AuthService authService;

    @PostMapping(path = "/register/employee")
    public ResponseEntity<?> registerEmployee(@RequestBody AuthRequest request){
        RegisterResponse register = authService.registerEmployee(request);
        CommonResponse<Object> commonResponse = CommonResponse.builder()
                .statusCode(HttpStatus.CREATED.value())
                .message("Successfully registration employee")
                .data(register)
                .build();
        return ResponseEntity.status(HttpStatus.CREATED).body(commonResponse);
    }
    @PostMapping(path = "/register/manager")
    public ResponseEntity<?> registerManager(@RequestBody AuthRequest request){
        RegisterResponse register = authService.registerManager(request);
        CommonResponse<Object> commonResponse = CommonResponse.builder()
                .statusCode(HttpStatus.CREATED.value())
                .message("Successfully registration manager")
                .data(register)
                .build();
        return ResponseEntity.status(HttpStatus.CREATED).body(commonResponse);
    }
    @PostMapping(path = "/login")
    public ResponseEntity<?> login(@RequestBody AuthRequest request){
        LoginResponse login = authService.login(request);
        CommonResponse<Object> commonResponse = CommonResponse.builder()
                .statusCode(HttpStatus.OK.value())
                .message("Successfully login")
                .data(login)
                .build();
        return ResponseEntity.status(HttpStatus.OK).body(commonResponse);
    }
}
