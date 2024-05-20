package com.security.controller;

import com.security.controller.request.LoginRequest;
import com.security.controller.request.Oauth2GoogleRequest;
import com.security.controller.request.RegisterRequest;
import com.security.controller.response.AuthenticationResponse;
import com.security.service.AuthenticationService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.net.http.HttpResponse;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
@CrossOrigin("*")
public class AuthenticationController {
    private final AuthenticationService service ;
    @PostMapping("/register")
    public ResponseEntity<AuthenticationResponse> register(
            @RequestBody RegisterRequest request, HttpServletResponse servletResponse
    ) {
        return ResponseEntity.ok(service.register(servletResponse,request));
    }
    @PostMapping("/authenticate")
    public ResponseEntity<AuthenticationResponse> authenticate(
            @RequestBody LoginRequest request, HttpServletResponse servletResponse
    ) {
        return ResponseEntity.ok(service.loginUser(servletResponse, request));
    }
    @PostMapping("/oauth2Google")
    public ResponseEntity<AuthenticationResponse> oauth2Google(
            @RequestBody Oauth2GoogleRequest request
    ) throws IOException {
        return ResponseEntity.ok(service.loginOauth2Google(request));
    }
    @GetMapping("/resetToken")
    public ResponseEntity<String> resetToken(
    ) {

        return ResponseEntity.ok("Hello");
    }
}
