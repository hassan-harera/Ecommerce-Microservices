package com.harera.ecommerce.authorization.service.auth;

import static com.harera.ecommerce.framework.util.RegexUtils.*;

import java.util.Objects;
import java.util.Optional;

import org.apache.commons.lang3.StringUtils;
import org.modelmapper.ModelMapper;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.harera.ecommerce.authorization.service.jwt.JwtService;
import com.harera.ecommerce.authorization.service.jwt.JwtUtils;
import com.harera.ecommerce.authorization.service.keycloak.KeycloakService;
import com.harera.ecommerce.authorization.model.auth.LoginRequest;
import com.harera.ecommerce.authorization.model.auth.LoginResponse;
import com.harera.ecommerce.authorization.model.auth.LogoutRequest;
import com.harera.ecommerce.authorization.model.auth.SignupRequest;
import com.harera.ecommerce.authorization.model.auth.SignupResponse;
import com.harera.ecommerce.authorization.model.user.User;
import com.harera.ecommerce.authorization.repository.TokenRepository;
import com.harera.ecommerce.authorization.repository.UserRepository;
import com.harera.ecommerce.authorization.service.firebase.FirebaseServiceImpl;

@Service
public class AuthService {

    private final UserRepository userRepository;
    private final JwtService jwtService;
    private final PasswordEncoder passwordEncoder;
    private final AuthValidation authValidation;
    private final TokenRepository tokenRepository;
    private final FirebaseServiceImpl firebaseServiceImpl;
    private final ModelMapper modelMapper;
    private final JwtUtils jwtUtils;
    private final KeycloakService keycloakService;

    public AuthService(UserRepository userRepository, JwtService jwtService,
                       PasswordEncoder passwordEncoder, AuthValidation authValidation,
                       TokenRepository tokenRepository,
                       FirebaseServiceImpl firebaseServiceImpl, ModelMapper modelMapper,
                       JwtUtils jwtUtils, KeycloakService keycloakService) {
        this.userRepository = userRepository;
        this.jwtService = jwtService;
        this.passwordEncoder = passwordEncoder;
        this.authValidation = authValidation;
        this.tokenRepository = tokenRepository;
        this.firebaseServiceImpl = firebaseServiceImpl;
        this.modelMapper = modelMapper;
        this.jwtUtils = jwtUtils;
        this.keycloakService = keycloakService;
    }

    public LoginResponse login(LoginRequest loginRequest) {
        authValidation.validateLogin(loginRequest);

        long userId = getUserId(loginRequest.getSubject());
        User user = userRepository.findById(userId).orElseThrow(
                () -> new UsernameNotFoundException("User not found"));

        if (!Objects.equals(user.getDeviceToken(), loginRequest.getDeviceToken())) {
            user.setDeviceToken(loginRequest.getDeviceToken());
            userRepository.save(user);
        }

        return keycloakService.login(loginRequest);
    }

    public SignupResponse signup(SignupRequest signupRequest) {
        authValidation.validate(signupRequest);
        User user = modelMapper.map(signupRequest, User.class);
        user.setPassword(passwordEncoder.encode(signupRequest.getPassword()));
        keycloakService.signup(user, signupRequest.getPassword());
        user = userRepository.save(user);
        user.setUsername(user.getId().toString());
        userRepository.save(user);
        return modelMapper.map(user, SignupResponse.class);
    }

    public void logout(LogoutRequest logoutRequest) {
        String usernameOrMobile = jwtUtils.extractUserSubject(logoutRequest.getToken());
        final User user = (User) loadUserByUsername(usernameOrMobile);
        if (StringUtils.isNotEmpty(user.getDeviceToken())) {
            user.setDeviceToken(null);
            userRepository.save(user);
        }
        jwtUtils.validateToken(user, logoutRequest.getToken());
        tokenRepository.removeUserToken(logoutRequest.getToken());
        if (StringUtils.isNotEmpty(logoutRequest.getRefreshToken())) {
            jwtUtils.validateRefreshToken(user, logoutRequest.getRefreshToken());
            tokenRepository.removeUserRefreshToken(logoutRequest.getRefreshToken());
        }
    }

    private long getUserId(String subject) {
        Optional<User> user = Optional.empty();
        if (isPhoneNumber(subject)) {
            user = userRepository.findByMobile(subject);
        } else if (isEmail(subject)) {
            user = userRepository.findByEmail(subject);
        } else if (isUsername(subject)) {
            user = userRepository.findByUsername(subject);
        }
        if (user.isPresent()) {
            return user.get().getId();
        }
        return 0;
    }

    public UserDetails loadUserByUsername(String username)
            throws UsernameNotFoundException {
        try {
            long userId = Integer.parseInt(username);
            return userRepository.findById(userId).orElse(null);
        } catch (Exception e) {
            throw new UsernameNotFoundException("User not found");
        }
    }

    public LoginResponse refresh(String refreshToken) {
        String usernameOrMobile = jwtUtils.extractUserSubject(refreshToken);
        final User user = (User) loadUserByUsername(usernameOrMobile);
        jwtUtils.validateRefreshToken(user, refreshToken);
        LoginResponse authResponse = new LoginResponse();
        authResponse.setToken(jwtService.generateToken(user));
        authResponse.setRefreshToken(jwtService.generateRefreshToken(user));
        return authResponse;
    }
}
