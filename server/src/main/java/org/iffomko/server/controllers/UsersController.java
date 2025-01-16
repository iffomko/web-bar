package org.iffomko.server.controllers;

import org.iffomko.server.domain.User;
import org.iffomko.server.services.UserService;
import org.springframework.web.bind.annotation.*;

import static org.iffomko.server.domain.ControllerNames.*;

@RestController
@RequestMapping(BASE_URL + USERS_URI_PART)
@CrossOrigin("http://localhost:4200/")
public class UsersController {
    private final UserService userService;

    public UsersController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping(LOGIN_URI_PART)
    public User login(@RequestBody User user) {
        return userService.login(user);
    }

    @PostMapping(REGISTRATION_URI_PART)
    public User register(@RequestBody User user) {
        return userService.register(user);
    }
}
