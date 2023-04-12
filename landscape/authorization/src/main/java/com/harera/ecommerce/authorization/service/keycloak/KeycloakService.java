package com.harera.ecommerce.authorization.service.keycloak;

import com.harera.ecommerce.authorization.model.auth.LoginRequest;
import com.harera.ecommerce.authorization.model.auth.LoginResponse;
import com.harera.ecommerce.authorization.model.user.User;


public interface KeycloakService {

    LoginResponse login(LoginRequest request);

    void signup(User user, String rawPassword);
}
