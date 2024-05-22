package com.security.service.impl;

import com.google.api.client.googleapis.auth.oauth2.GoogleAuthorizationCodeTokenRequest;
import com.google.api.client.googleapis.auth.oauth2.GoogleClientSecrets;
import com.google.api.client.googleapis.auth.oauth2.GoogleIdToken;
import com.google.api.client.googleapis.auth.oauth2.GoogleTokenResponse;
import com.google.api.client.http.javanet.NetHttpTransport;
import com.google.api.client.json.gson.GsonFactory;
import com.security.constant.TokenType;
import com.security.controller.request.LoginRequest;
import com.security.controller.request.Oauth2GoogleRequest;
import com.security.controller.request.RegisterRequest;
import com.security.controller.response.AuthenticationResponse;
import com.security.entity.Token;
import com.security.entity.UserLogin;
import com.security.repository.TokenRepository;
import com.security.repository.UserLoginRepository;
import com.security.service.AuthenticationService;
import com.security.service.JwtService;
import com.security.util.CookieUtil;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.io.IOException;

@Slf4j
@Service
@RequiredArgsConstructor
public class AuthenticationServiceImpl implements AuthenticationService {
    @Value("${spring.security.oauth2.client.registration.google.client-id}")
    private String googleClientId;
    @Value("${spring.security.oauth2.client.registration.google.client-secret}")
    private String googleClientSecret;
    private final UserLoginRepository repository;
    private final TokenRepository tokenRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;
    private final AuthenticationManager authenticationManager;
    @Override
    public AuthenticationResponse register(HttpServletResponse servletResponse,RegisterRequest request) {
        var user = UserLogin.builder()
                .name(request.getName())
                .username(request.getUsername())
                .password(passwordEncoder.encode(request.getPassword()))
                .build();
        var savedUser = repository.save(user);
        var jwtToken = jwtService.generateToken(user);
        var refreshToken = jwtService.generateRefreshToken(user);
        saveUserToken(savedUser, jwtToken);
        CookieUtil.addCookie(servletResponse,"refreshToken",refreshToken,360);
        CookieUtil.addCookie(servletResponse,"jwtToken",jwtToken,20,false);
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
    public AuthenticationResponse loginUser(HttpServletResponse servletResponse, LoginRequest request) {
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        request.getUserName(),
                        request.getPassword()
                )
        );
        var user = repository.findByUsername(request.getUserName())
                .orElseThrow();
        var jwtToken = jwtService.generateToken(user);
        var refreshToken = jwtService.generateRefreshToken(user);
        revokeAllUserTokens(user);
        saveUserToken(user, jwtToken);
        CookieUtil.addCookie(servletResponse,"refreshToken",refreshToken,360);
        CookieUtil.addCookie(servletResponse,"jwtToken",jwtToken,20,false);
        return AuthenticationResponse.builder()
                .accessToken(jwtToken)
                .refreshToken(refreshToken)
                .build();
    }
    private void revokeAllUserTokens(UserLogin user) {
        var validUserTokens = tokenRepository.findAllValidTokenByUser(user.getUserId());
        if (validUserTokens.isEmpty())
            return;
        validUserTokens.forEach(token -> {
            token.setRevoked(true);
        });
        tokenRepository.saveAll(validUserTokens);
    }

    @Override
    public AuthenticationResponse loginOauth2Google(Oauth2GoogleRequest request) throws IOException {
//        GoogleClientSecrets clientSecrets =
//                GoogleClientSecrets.load(GsonFactory.getDefaultInstance(), new FileReader("E:\\New folder\\sercurity\\backend\\src\\main\\resources\\cert\\client_secret.json"));
        GoogleTokenResponse tokenResponse =
                new GoogleAuthorizationCodeTokenRequest(
                        new NetHttpTransport(),
                        GsonFactory.getDefaultInstance(),
                        googleClientId,
                        googleClientSecret,
                        request.getCode(),"http://localhost:5173").execute();
        String accessToken = tokenResponse.getAccessToken();
        GoogleIdToken idToken = tokenResponse.parseIdToken();
        GoogleIdToken.Payload payload = idToken.getPayload();
        log.info("data"+tokenResponse.getIdToken());
        return null;
    }

    @Override
    public AuthenticationResponse refreshToken(String token) {

        return null;
    }
}
