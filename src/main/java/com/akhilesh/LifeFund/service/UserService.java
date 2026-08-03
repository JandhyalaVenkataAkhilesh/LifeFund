package com.akhilesh.LifeFund.service;

import com.akhilesh.LifeFund.dto.request.LoginRequest;
import com.akhilesh.LifeFund.dto.request.SignupRequest;
import com.akhilesh.LifeFund.dto.response.LoginResponse;
import com.akhilesh.LifeFund.dto.response.SignupResponse;

public interface UserService {

    SignupResponse signup(SignupRequest request);

    LoginResponse login(LoginRequest request);

}