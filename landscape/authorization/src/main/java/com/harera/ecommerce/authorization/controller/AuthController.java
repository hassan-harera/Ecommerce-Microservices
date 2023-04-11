package com.harera.ecommerce.authorization.controller;

import static org.springframework.http.ResponseEntity.ok;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.harera.ecommerce.authorization.model.auth.LoginRequest;
import com.harera.ecommerce.authorization.model.auth.LoginResponse;
import com.harera.ecommerce.authorization.model.auth.LogoutRequest;
import com.harera.ecommerce.authorization.model.auth.SignupRequest;
import com.harera.ecommerce.authorization.model.auth.SignupResponse;
import com.harera.ecommerce.authorization.service.auth.AuthService;

import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.extern.log4j.Log4j2;

@Log4j2
@RestController
@RequestMapping("/api/v1/auth")
@Tag(name = "Auth", description = "Auth API")
public class AuthController {

    private final AuthService authService;

    public AuthController(AuthService authService) {
        this.authService = authService;
    }

    @PostMapping("/login")
    public ResponseEntity<LoginResponse> login(@RequestBody LoginRequest loginRequest) {
        return ok(authService.login(loginRequest));
    }

    @PostMapping("/signup")
    public ResponseEntity<SignupResponse> signup(@RequestBody SignupRequest signupRequest) {
        return ok(authService.signup(signupRequest));
    }

    @PostMapping("/logout")
    public ResponseEntity<Void> logout(@RequestBody LogoutRequest logoutRequest) {
        authService.logout(logoutRequest);
        return ok().build();
    }
}