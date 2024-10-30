package edu.lukewilson.StreetFighter.Controller;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import edu.lukewilson.StreetFighter.model.Match;
import edu.lukewilson.StreetFighter.model.User;
import edu.lukewilson.StreetFighter.service.MatchService;
import edu.lukewilson.StreetFighter.service.UserService;

@RestController
@RequestMapping("/api")
public class MatchController {
    @Autowired
    private MatchService matchService;

    @Autowired
    private UserService userService;

    @PostMapping("/matches")
    public Match addMatch(@RequestBody Match match, Authentication authentication) {
        Jwt jwt = (Jwt) authentication.getPrincipal();
        String auth0Id = jwt.getSubject();
        
        User user = userService.findUserByAuth0Id(auth0Id);
        match.setUser(user);
        match.setMatchTimestamp(LocalDateTime.now());
        
        return matchService.addMatch(match);
    }

    @GetMapping("/matches")
    public ResponseEntity<Map<String, Object>> getUserMatches(Authentication authentication) {
        Jwt jwt = (Jwt) authentication.getPrincipal();
        String auth0Id = jwt.getSubject();
        
        User user = userService.findUserByAuth0Id(auth0Id);
        if (user == null) {
            return ResponseEntity.badRequest().build();
        }
        
        List<Match> matches = matchService.getMatchesByUser(user);
        Long matchCount = matchService.getMatchCountByUser(user);

        //hashmap of pairs of matches and total matches
        Map<String, Object> response = new HashMap<>();
        response.put("matches", matches);
        response.put("totalMatches", matchCount);
        
        return ResponseEntity.ok(response);
    }
}
