package com.security.service;

import com.security.controller.request.LoginRequest;
import com.security.controller.request.Oauth2GoogleRequest;
import com.security.controller.request.RegisterRequest;
import com.security.controller.response.AuthenticationResponse;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.core.Authentication;

import java.io.FileNotFoundException;
import java.io.IOException;

public interface AuthenticationService {
    AuthenticationResponse register(HttpServletResponse servletResponse,RegisterRequest request);

    AuthenticationResponse loginUser(HttpServletResponse response, LoginRequest request);

    AuthenticationResponse loginOauth2Google(Oauth2GoogleRequest request) throws IOException;

    AuthenticationResponse refreshToken(String token);
}
