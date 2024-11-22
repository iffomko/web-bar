package org.iffomko.server.controllers;

import jakarta.annotation.security.PermitAll;
import org.iffomko.server.domain.User;
import org.iffomko.server.exceptions.LocalizedException;
import org.iffomko.server.services.UserService;
import org.springframework.web.bind.annotation.*;

import static org.iffomko.server.domain.ControllerNames.*;

@RestController
@RequestMapping(BASE_URL + USERS_URI_PART)
public class UsersController {
    private static final String USER_NOT_FOUND_MESSAGE = "validation.user.not-found";

    private final UserService userService;

    public UsersController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping
    public User loadUser(@RequestParam String phone) {
        return userService.byPhone(phone)
                .orElseThrow(() -> new LocalizedException(USER_NOT_FOUND_MESSAGE, phone));
    }

    @PostMapping(REGISTRATION_URI_PART)
    public void register(@RequestBody User user) {
        userService.register(user);
    }
}
