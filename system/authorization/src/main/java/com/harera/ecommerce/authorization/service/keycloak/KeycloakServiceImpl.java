package com.harera.ecommerce.authorization.service.keycloak;

import lombok.extern.log4j.Log4j2;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.keycloak.admin.client.Keycloak;
import org.keycloak.admin.client.KeycloakBuilder;
import org.keycloak.representations.idm.CredentialRepresentation;
import org.keycloak.representations.idm.UserRepresentation;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.harera.ecommerce.authorization.model.auth.LoginResponse;
import com.harera.ecommerce.authorization.model.user.User;
import com.harera.ecommerce.framework.exception.LoginException;
import com.harera.ecommerce.framework.exception.SignupException;
import com.harera.ecommerce.framework.util.ErrorCode;

import static java.lang.String.valueOf;
import static java.util.Collections.singletonList;
import static java.util.List.of;

@Service
@Log4j2
@Transactional
public class KeycloakServiceImpl implements KeycloakService {

    private final Keycloak keycloak;
    private final ModelMapper modelMapper;
    private final String clientId;
    private final String serverUrl;
    private final String realm;
    private final String clientSecret;

    public KeycloakServiceImpl(Keycloak keycloak, ModelMapper modelMapper,
                               @Value("${keycloak.auth-server-url}") String serverUrl,
                               @Value("${keycloak.realm}") String realm,
                               @Value("${keycloak.credentials.client-id}") String clientId,
                               @Value("${keycloak.credentials.secret}") String clientSecret,
                               @Value("${keycloak.credentials.username}") String username,
                               @Value("${keycloak.credentials.password}") String password) {
        this.keycloak = keycloak;
        this.modelMapper = modelMapper;
        this.clientId = clientId;
        this.serverUrl = serverUrl;
        this.realm = realm;
        this.clientSecret = clientSecret;
    }

    @Override
    public LoginResponse login(String username, String password) {
        Keycloak loginKeycloak = buildLoginKeycloak(username, password);
        try {
            return modelMapper.map(loginKeycloak.tokenManager().getAccessToken(),
                    LoginResponse.class);
        } catch (Exception e) {
            log.error(e);
            throw new LoginException(ErrorCode.INVALID_LOGIN_CREDENTIALS,
                    "Invalid login credentials");
        }
    }

    @Override
    public void signup(User user) {
        UserRepresentation userRepresentation =
                modelMapper.map(user, UserRepresentation.class);
        userRepresentation.setEnabled(true);
        userRepresentation.setEmailVerified(true);

        Map<String, List<String>> attributes = new HashMap<>();
        attributes.put("id", of(valueOf(user.getId())));
        attributes.put("uid", of(user.getUid()));
        attributes.put("username", of(user.getUsername()));
        userRepresentation.setAttributes(attributes);

        CredentialRepresentation credentialRepresentation =
                new CredentialRepresentation();
        credentialRepresentation.setType("password");
        credentialRepresentation.setValue(user.getPassword());
        credentialRepresentation.setTemporary(false);
        userRepresentation.setCredentials(singletonList(credentialRepresentation));

        HashMap<String, List<String>> clientRoles = new HashMap<>();
        clientRoles.put(clientId, singletonList("user"));
        userRepresentation.setClientRoles(clientRoles);

        try {
            keycloak.realm(realm).users().create(userRepresentation);
        } catch (Exception ex) {
            log.error(ex);
            throw new SignupException("Error while processing creating");
        }
    }

    public void logout(String token, String refreshToken) {
        try {
            keycloak.tokenManager().invalidate(token);
            keycloak.tokenManager().invalidate(refreshToken);
        } catch (Exception ex) {
            log.error(ex);
        }
    }

    @Override
    public void resetPassword(User user, String newPassword) {
        CredentialRepresentation credentialRepresentation =
                new CredentialRepresentation();
        credentialRepresentation.setType("password");
        credentialRepresentation.setValue(newPassword);
        credentialRepresentation.setTemporary(false);

        keycloak.realm(realm).users().get(user.getMobile())
                .resetPassword(credentialRepresentation);
    }

    private Keycloak buildLoginKeycloak(String username, String password) {
        return KeycloakBuilder.builder().realm(realm).serverUrl(serverUrl)
                .clientId(clientId).clientSecret(clientSecret).username(username)
                .password(password).build();
    }
}
