package com.harera.ecommerce.authorization.controller;

import static org.springframework.http.ResponseEntity.ok;

import com.harera.ecommerce.authorization.service.oauth.OauthService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.harera.ecommerce.authorization.model.auth.LoginResponse;
import com.harera.ecommerce.authorization.model.auth.SignupResponse;
import com.harera.ecommerce.authorization.model.oauth.OauthLoginRequest;
import com.harera.ecommerce.authorization.model.oauth.OauthSignupRequest;

import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.extern.log4j.Log4j2;

@Log4j2
@RestController
@RequestMapping("/api/v1/oauth")
@Tag(name = "Oauth", description = "Oauth API")
public class OauthController {

    private final OauthService oauthService;

    public OauthController(OauthService oauthService) {
        this.oauthService = oauthService;
    }

    @PostMapping("/login")
    public ResponseEntity<LoginResponse> login(
                    @RequestBody OauthLoginRequest loginRequest) {
        return ok(oauthService.login(loginRequest));
    }

    @PostMapping("/signup")
    public ResponseEntity<SignupResponse> oauthSignup(
            @RequestBody OauthSignupRequest oauthSignupRequest) {
        return ok(oauthService.signup(oauthSignupRequest));
    }
}
