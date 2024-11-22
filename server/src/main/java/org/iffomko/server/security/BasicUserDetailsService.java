package org.iffomko.server.security;

import org.iffomko.server.domain.security.BasicUserDetails;
import org.iffomko.server.services.UserService;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.ResourceBundle;

@Service
public class BasicUserDetailsService implements UserDetailsService {
    private static final String USER_NOT_FOUND_MESSAGE = "auth.basic.user-not-found";
    private static final String EMPTY_PHONE_PRETTY_PRINT = "(empty phone)";

    private static final ResourceBundle resourceBundle = ResourceBundle.getBundle("messages");

    private final UserService userService;

    public BasicUserDetailsService(UserService userService) {
        this.userService = userService;
    }

    @Override
    public UserDetails loadUserByUsername(String phone) throws UsernameNotFoundException {
        return userService.byPhone(phone)
                .map(BasicUserDetails::fromUser)
                .orElseThrow(() -> new UsernameNotFoundException(
                        getMessage(USER_NOT_FOUND_MESSAGE, getPrettyPhone(phone))));
    }

    private String getMessage(String key, Object... args) {
        String messagePattern = resourceBundle.getString(key);
        return args == null ? messagePattern : String.format(messagePattern, args);
    }

    private String getPrettyPhone(String phone) {
        return phone == null || phone.isBlank() || phone.isEmpty() ? EMPTY_PHONE_PRETTY_PRINT : phone;
    }
}
