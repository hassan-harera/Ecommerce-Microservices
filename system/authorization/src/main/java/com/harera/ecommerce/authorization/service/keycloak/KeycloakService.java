package com.harera.ecommerce.authorization.service.keycloak;

import com.harera.ecommerce.authorization.model.auth.LoginResponse;
import com.harera.ecommerce.authorization.model.user.User;


public interface KeycloakService {

    LoginResponse login(String username, String password);

    void signup(User user);

    void logout(String token, String refreshToken);

    void resetPassword(User user, String newPassword);
}
