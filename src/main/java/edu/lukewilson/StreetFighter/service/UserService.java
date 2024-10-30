package edu.lukewilson.StreetFighter.service;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

import edu.lukewilson.StreetFighter.model.User;
import edu.lukewilson.StreetFighter.repository.UserRepository;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    @Value("${spring.security.oauth2.client.provider.auth0.issuer-uri}")
    private String issuerUri;

    private final WebClient webClient;

    public UserService(WebClient.Builder webClientBuilder) {
        this.webClient = webClientBuilder.build();
    }

    public User getOrCreateUser(Jwt jwt) {
        String auth0Id = jwt.getSubject();
        Map<String, Object> userInfo = getUserInfo(jwt.getTokenValue());
        
        return userRepository.findByAuth0Id(auth0Id)
            .map(user -> updateUser(user, userInfo))
            .orElseGet(() -> createUser(auth0Id, userInfo));
    }

    private User createUser(String auth0Id, Map<String, Object> userInfo) {
        User user = new User();
        user.setAuth0Id(auth0Id);
        user.setEmail((String) userInfo.get("email"));
        user.setName((String) userInfo.get("name"));
        return userRepository.save(user);
    }

    private User updateUser(User user, Map<String, Object> userInfo) {
        user.setEmail((String) userInfo.get("email"));
        user.setName((String) userInfo.get("name"));
        return userRepository.save(user);
    }

    private Map<String, Object> getUserInfo(String accessToken) {
        return webClient.get()
                .uri(issuerUri + "userinfo")
                .headers(h -> h.setBearerAuth(accessToken))
                .retrieve()
                .bodyToMono(Map.class)
                .block();
    }

    public User findUserByAuth0Id(String auth0Id) {
        return userRepository.findByAuth0Id(auth0Id)
            .orElseThrow(() -> new RuntimeException("User not found"));
    }
}
