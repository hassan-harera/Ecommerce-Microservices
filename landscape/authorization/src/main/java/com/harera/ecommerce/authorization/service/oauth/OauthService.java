package com.harera.ecommerce.authorization.service.oauth;

import java.util.Optional;

import org.apache.commons.lang3.StringUtils;
import org.modelmapper.ModelMapper;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.google.firebase.auth.FirebaseToken;
import com.google.firebase.auth.UserRecord;
import com.harera.ecommerce.authorization.model.auth.FirebaseOauthToken;
import com.harera.ecommerce.authorization.model.auth.LoginRequest;
import com.harera.ecommerce.authorization.model.auth.LoginResponse;
import com.harera.ecommerce.authorization.model.auth.LogoutRequest;
import com.harera.ecommerce.authorization.model.auth.SignupResponse;
import com.harera.ecommerce.authorization.model.oauth.OAuthLoginRequest;
import com.harera.ecommerce.authorization.model.oauth.OauthSignupRequest;
import com.harera.ecommerce.authorization.model.user.AuthUser;
import com.harera.ecommerce.authorization.model.user.FirebaseUser;
import com.harera.ecommerce.authorization.repository.TokenRepository;
import com.harera.ecommerce.authorization.repository.UserRepository;
import com.harera.ecommerce.authorization.service.auth.AuthValidation;
import com.harera.ecommerce.authorization.service.firebase.FirebaseServiceImpl;
import com.harera.ecommerce.authorization.service.jwt.JwtService;
import com.harera.ecommerce.authorization.service.jwt.JwtUtils;
import com.harera.ecommerce.framework.exception.SignupException;

import static com.harera.ecommerce.framework.util.RegexUtils.isEmail;
import static com.harera.ecommerce.framework.util.RegexUtils.isPhoneNumber;
import static com.harera.ecommerce.framework.util.RegexUtils.isUsername;

@Service
public class OauthService {

    private final UserRepository userRepository;
    private final JwtService jwtService;
    private final PasswordEncoder passwordEncoder;
    private final AuthValidation authValidation;
    private final TokenRepository tokenRepository;
    private final FirebaseServiceImpl firebaseServiceImpl;
    private final ModelMapper modelMapper;
    private final JwtUtils jwtUtils;

    public OauthService(UserRepository userRepository, JwtService jwtService,
                        PasswordEncoder passwordEncoder, AuthValidation authValidation,
                        TokenRepository tokenRepository,
                        FirebaseServiceImpl firebaseServiceImpl, ModelMapper modelMapper,
                        JwtUtils jwtUtils) {
        this.userRepository = userRepository;
        this.jwtService = jwtService;
        this.passwordEncoder = passwordEncoder;
        this.authValidation = authValidation;
        this.tokenRepository = tokenRepository;
        this.firebaseServiceImpl = firebaseServiceImpl;
        this.modelMapper = modelMapper;
        this.jwtUtils = jwtUtils;
    }

    public LoginResponse login(OAuthLoginRequest oAuthLoginRequest) {
        authValidation.validate(oAuthLoginRequest);
        FirebaseToken firebaseToken = firebaseServiceImpl
                .getFirebaseToken(oAuthLoginRequest.getFirebaseToken());
        UserRecord userRecord = firebaseServiceImpl.getUser(firebaseToken.getUid());
        AuthUser user = userRepository.findByUid(userRecord.getUid()).orElseThrow(
                () -> new UsernameNotFoundException("User not found"));
        return new LoginResponse(jwtService.generateToken(user),
                jwtService.generateRefreshToken(user));
    }

    public SignupResponse signup(OauthSignupRequest signupRequest) {
        authValidation.validate(signupRequest);
        FirebaseUser firebaseUser = firebaseServiceImpl.createUser(signupRequest);
        if (firebaseUser == null) {
            throw new SignupException("User creation failed");
        }

        AuthUser user = modelMapper.map(signupRequest, AuthUser.class);
        user.setPassword(passwordEncoder.encode(signupRequest.getPassword()));
        user.setUid(firebaseUser.getUid());
        user.setUsername(firebaseUser.getUid());

        userRepository.save(user);
        return modelMapper.map(user, SignupResponse.class);
    }

    private long getUserId(String subject) {
        Optional<AuthUser> user = Optional.empty();
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

    public FirebaseOauthToken generateFirebaseToken(LoginRequest loginRequest) {
        authValidation.validateLogin(loginRequest);
        long userId = getUserId(loginRequest.getSubject());
        AuthUser user = userRepository.findById(userId).orElseThrow(
                () -> new UsernameNotFoundException("User not found"));
        String s = firebaseServiceImpl.generateToken(user.getUid());
        return new FirebaseOauthToken(s);
    }
}
