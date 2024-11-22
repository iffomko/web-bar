package org.iffomko.server.services;

import org.iffomko.server.domain.User;
import org.iffomko.server.exceptions.LocalizedException;
import org.iffomko.server.repositories.UserRepository;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserService {
    private static final String INVALID_FORMAT_PHONE_NUMBER_MESSAGE = "validation.user.phone.invalid";
    private static final String INVALID_FORMAT_PASSWORD_MESSAGE = "validation.user.password.invalid";
    private static final String INVALID_ROLE_MESSAGE = "validation.user.role.invalid";
    private static final String USER_ALREADY_EXISTS_MESSAGE = "validation.user.already-exists";
    public static final int PASSWORD_MIN_SIZE = 6;

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public UserService(UserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    public Optional<User> byPhone(String phone) {
        return userRepository.findByPhone(phone);
    }

    public void register(User user) {
        this.validateUser(user);
        if (byPhone(user.getPhone()).isPresent()) {
            throw new LocalizedException(USER_ALREADY_EXISTS_MESSAGE);
        }
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        userRepository.save(user);
    }

    private void validateUser(User user) {
        if (user.getPhone() == null || user.getPhone().isEmpty()) {
            throw new LocalizedException(INVALID_FORMAT_PHONE_NUMBER_MESSAGE);
        }
        if (user.getPassword() == null || user.getPassword().isEmpty() || user.getPassword().length() < PASSWORD_MIN_SIZE) {
            throw new LocalizedException(INVALID_FORMAT_PASSWORD_MESSAGE);
        }
        if (user.getRole() == null) {
            throw new LocalizedException(INVALID_ROLE_MESSAGE);
        }
    }
}
