package com.akhilesh.LifeFund.controller;

import com.akhilesh.LifeFund.dto.request.LoginRequest;
import com.akhilesh.LifeFund.dto.request.SignupRequest;
import com.akhilesh.LifeFund.dto.response.LoginResponse;
import com.akhilesh.LifeFund.dto.response.SignupResponse;
import com.akhilesh.LifeFund.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthController {

    private final UserService userService;

    @PostMapping("/signup")
    public ResponseEntity<SignupResponse> signup(
            @RequestBody SignupRequest request) {

        SignupResponse response = userService.signup(request);

        return new ResponseEntity<>(response, HttpStatus.CREATED);

    }

    @PostMapping("/login")
    public ResponseEntity<LoginResponse> login(
            @RequestBody LoginRequest request) {

        LoginResponse response = userService.login(request);

        return ResponseEntity.ok(response);

    }

    @GetMapping("/me")
    public ResponseEntity<String> me() {
        return ResponseEntity.ok("JWT Working Successfully");
    }

}