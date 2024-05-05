package com.security.service;

import com.security.controller.request.LoginRequest;
import com.security.controller.request.RegisterRequest;
import com.security.controller.response.AuthenticationResponse;

public interface AuthenticationService {
    AuthenticationResponse register(RegisterRequest request);

    AuthenticationResponse login(LoginRequest request);
}
