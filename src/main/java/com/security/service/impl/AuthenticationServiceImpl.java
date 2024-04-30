package com.security.service.impl;

import com.security.controller.request.LoginRequest;
import com.security.controller.request.RegisterRequest;
import com.security.controller.response.AuthenticationResponse;
import com.security.service.AuthenticationService;
import org.springframework.stereotype.Service;

@Service
public class AuthenticationServiceImpl implements AuthenticationService {
    @Override
    public AuthenticationResponse register(RegisterRequest request) {
        return null;
    }

    @Override
    public AuthenticationResponse login(LoginRequest request) {
        return null;
    }
}
