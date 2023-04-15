package com.harera.ecommerce.authorization.service;

import com.harera.ecommerce.authorization.model.user.User;
import com.harera.ecommerce.authorization.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

import static com.harera.ecommerce.framework.util.RegexUtils.*;

@Service
@RequiredArgsConstructor
public class UserUtils {

    private final UserRepository userRepository;

    public long getUserId(String subject) {
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
}
