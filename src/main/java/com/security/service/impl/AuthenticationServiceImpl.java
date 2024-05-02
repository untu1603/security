package com.security.service.impl;

import com.security.constant.TokenType;
import com.security.controller.request.LoginRequest;
import com.security.controller.request.RegisterRequest;
import com.security.controller.response.AuthenticationResponse;
import com.security.entity.Token;
import com.security.entity.UserLogin;
import com.security.repository.TokenRepository;
import com.security.repository.UserLoginRepository;
import com.security.service.AuthenticationService;
import com.security.service.JwtService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthenticationServiceImpl implements AuthenticationService {
    private final UserLoginRepository repository;
    private final TokenRepository tokenRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;
    @Override
    public AuthenticationResponse register(RegisterRequest request) {
        var user = UserLogin.builder()

                .username(request.getUsername())
                .password(passwordEncoder.encode(request.getPassword()))
                .build();
        var savedUser = repository.save(user);
        var jwtToken = jwtService.generateToken(user);
        var refreshToken = jwtService.generateRefreshToken(user);
        saveUserToken(savedUser, jwtToken);
        return AuthenticationResponse.builder()
                .accessToken(jwtToken)
                .refreshToken(refreshToken)
                .build();
    }
    private void saveUserToken(UserLogin user, String jwtToken) {
        var token = Token.builder()
                .user(user)
                .token(jwtToken)
                .tokenType(TokenType.BEARER)
                .expired(false)
                .revoked(false)
                .build();
        tokenRepository.save(token);
    }
    @Override
    public AuthenticationResponse login(LoginRequest request) {
        return null;
    }
}
