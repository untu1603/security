package com.security.service.impl;

import com.google.api.client.googleapis.auth.oauth2.GoogleAuthorizationCodeTokenRequest;
import com.google.api.client.googleapis.auth.oauth2.GoogleClientSecrets;
import com.google.api.client.googleapis.auth.oauth2.GoogleIdToken;
import com.google.api.client.googleapis.auth.oauth2.GoogleTokenResponse;
import com.google.api.client.http.javanet.NetHttpTransport;
import com.google.api.client.json.gson.GsonFactory;
import com.security.constant.TokenName;
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
import java.util.ArrayList;
import java.util.List;

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
        var accessToken = jwtService.generateToken(user);
        var refreshToken = jwtService.generateRefreshToken(user);
        saveUserToken(savedUser, accessToken, refreshToken);
        CookieUtil.addCookie(servletResponse,"refreshToken",refreshToken,360);
        CookieUtil.addCookie(servletResponse,"accessToken",accessToken,20,false);
        return AuthenticationResponse.builder()
                .accessToken(accessToken)
                .refreshToken(refreshToken)
                .build();
    }
    private void saveUserToken(UserLogin user, String jwtToken, String refreshToken) {
        List<Token> tokens= new ArrayList<>();
        tokens.add(createToken(user,TokenName.ACCESSTOKEN,jwtToken));
        tokens.add(createToken(user,TokenName.REFRESHTOKEN,refreshToken));
        tokenRepository.saveAll(tokens);
    }
    private Token createToken(UserLogin user,TokenName nameToken, String value){
        return Token.builder()
                .name(nameToken)
                .user(user)
                .token(value)
                .tokenType(TokenType.BEARER)
                .revoked(false)
                .build();
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
        var accessToken = jwtService.generateToken(user);
        var refreshToken = jwtService.generateRefreshToken(user);
        revokeAllUserTokens(user);
        saveUserToken(user, accessToken, refreshToken);
        CookieUtil.addCookie(servletResponse,"refreshToken",refreshToken,360);
        CookieUtil.addCookie(servletResponse,"accessToken",accessToken,20,false);
        return AuthenticationResponse.builder()

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
        GoogleTokenResponse tokenResponse =
                new GoogleAuthorizationCodeTokenRequest(
                        new NetHttpTransport(),
                        GsonFactory.getDefaultInstance(),
                        googleClientId,
                        googleClientSecret,
                        request.getCode(),"http://localhost:5174").execute();
        String accessToken = tokenResponse.getAccessToken();
        String refreshToken = tokenResponse.getRefreshToken();
        GoogleIdToken idToken = tokenResponse.parseIdToken();
        GoogleIdToken.Payload payload = idToken.getPayload();
        log.info("data"+ payload.getSubject());
        return null;
    }

    @Override
    public AuthenticationResponse refreshToken(String token) {
        final String username = jwtService.extractUsername(token);

        return null;
    }
}
