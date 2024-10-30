package edu.lukewilson.StreetFighter.Controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import edu.lukewilson.StreetFighter.model.User;
import edu.lukewilson.StreetFighter.service.UserService;

@RestController
public class UserController {

    @Autowired
    private UserService userService;

    @PostMapping("/api/user")
    public User createOrUpdateUser(@AuthenticationPrincipal Jwt jwt) {
        return userService.getOrCreateUser(jwt);
    }
}
