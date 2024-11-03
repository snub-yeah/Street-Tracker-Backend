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
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import edu.lukewilson.StreetFighter.model.Match;
import edu.lukewilson.StreetFighter.model.PlayerCharacterStats;
import edu.lukewilson.StreetFighter.model.User;
import edu.lukewilson.StreetFighter.repository.PlayerCharacterStatsRepository;
import edu.lukewilson.StreetFighter.service.MatchService;
import edu.lukewilson.StreetFighter.service.UserService;

@RestController
@RequestMapping("/api")
public class MatchController {
    @Autowired
    private MatchService matchService;

    @Autowired
    private UserService userService;

    @Autowired
    private PlayerCharacterStatsRepository playerCharacterStatsRepository;

    /**
     * This allows users to add a match to the database. Takes in a match and their authentication, then saves to repo.
     * @param match --> the match info to be taken in including characters and time and stuff
     * @param authentication --> the authentication sutff
     * @return --> returns a match that is saved to repo.
     */
    @PostMapping("/matches")
    public Match addMatch(@RequestBody Match match, Authentication authentication) {
        User user = findUser(authentication);

        match.setUser(user);
        match.setMatchTimestamp(LocalDateTime.now());
        
        return matchService.addMatch(match);
    }

    @GetMapping("/matches")
    public ResponseEntity<Map<String, Object>> getUserMatches(Authentication authentication) {
        User user = findUser(authentication);
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

    @GetMapping("/character-stats")
    public PlayerCharacterStats getStatsByCharacter(
        @RequestParam(required = false) String characterName, @RequestParam(required = false) String opponentCharacter,
        Authentication authentication
    ) {
        User user = findUser(authentication);
        if (characterName == null) {
            return playerCharacterStatsRepository.findByUserAndPlayerCharacter(user.getId(), null);
        }
        if (opponentCharacter == null) {
            return playerCharacterStatsRepository.findByUserAndPlayerCharacter(user.getId(), characterName);
        }
        return playerCharacterStatsRepository.findByUserAndPlayerCharacterAndOpponentCharacter(user.getId(), characterName, opponentCharacter);
    }

    /**
     * Method to find the userId of the currently logged in user based on their auth0 info
     * @param authentication -- the authentication Auth0 class
     * @return --> the user
     */
    private User findUser(Authentication authentication) {
        Jwt jwt = (Jwt) authentication.getPrincipal();
        String auth0Id = jwt.getSubject();
        
        return userService.findUserByAuth0Id(auth0Id);
    }
}
