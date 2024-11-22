package org.iffomko.server.services;

import org.iffomko.server.domain.User;
import org.iffomko.server.exceptions.LocalizedException;
import org.iffomko.server.repositories.UserRepository;
import org.iffomko.server.validators.UserValidator;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserService {
    private static final String USER_ALREADY_EXISTS_MESSAGE = "validation.user.already-exists";

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final UserValidator userValidator;

    public UserService(UserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.userValidator = new UserValidator();
    }

    public Optional<User> byPhone(String phone) {
        return userRepository.findByPhone(phone);
    }

    public void register(User user) {
        userValidator.validate(user);
        if (byPhone(user.getPhone()).isPresent()) {
            throw new LocalizedException(USER_ALREADY_EXISTS_MESSAGE);
        }
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        userRepository.save(user);
    }
}
