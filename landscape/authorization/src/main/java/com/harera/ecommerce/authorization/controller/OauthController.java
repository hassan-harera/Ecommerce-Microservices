package com.harera.ecommerce.authorization.controller;

import static org.springframework.http.ResponseEntity.ok;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.harera.ecommerce.authorization.model.auth.FirebaseOauthToken;
import com.harera.ecommerce.authorization.model.auth.LoginRequest;
import com.harera.ecommerce.authorization.model.auth.LoginResponse;
import com.harera.ecommerce.authorization.model.auth.SignupResponse;
import com.harera.ecommerce.authorization.model.oauth.OAuthLoginRequest;
import com.harera.ecommerce.authorization.model.oauth.OauthSignupRequest;
import com.harera.ecommerce.authorization.service.oauth.OauthService;

import io.swagger.v3.oas.annotations.tags.Tag;

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
                    @RequestBody OAuthLoginRequest loginRequest) {
        return ok(oauthService.login(loginRequest));
    }

    @PostMapping("/signup")
    public ResponseEntity<SignupResponse> oauthSignup(
            @RequestBody OauthSignupRequest oauthSignupRequest) {
        return ok(oauthService.signup(oauthSignupRequest));
    }

    @PostMapping("/firebase/tokens")
    public ResponseEntity<FirebaseOauthToken> generateFirebaseToken(
                    @RequestBody LoginRequest loginRequest) {
        return ok(oauthService.generateFirebaseToken(loginRequest));
    }
}
