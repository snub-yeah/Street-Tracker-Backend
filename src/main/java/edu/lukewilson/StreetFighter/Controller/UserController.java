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

    /**
     * Creates or updates the user based on a JWT
     * @param jwt --> the web token of the authenticated user
     * @return --> the user created or returned
     */
    @PostMapping("/api/user")
    public User createOrUpdateUser(@AuthenticationPrincipal Jwt jwt) {
        return userService.getOrCreateUser(jwt);
    }
}
