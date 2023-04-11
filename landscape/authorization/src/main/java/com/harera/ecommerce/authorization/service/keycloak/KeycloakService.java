package com.harera.ecommerce.authorization.service.keycloak;

import com.harera.ecommerce.authorization.model.auth.LoginRequest;
import com.harera.ecommerce.authorization.model.auth.LoginResponse;
import com.harera.ecommerce.authorization.model.user.AuthUser;


public interface KeycloakService {

    LoginResponse login(LoginRequest request);

    void signup(AuthUser user, String rawPassword);
}
