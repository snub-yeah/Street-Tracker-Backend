package edu.lukewilson.StreetFighter.Controller;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
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

    /**
     * This is a method that will return ALL of the matches for a certain user. It does this by finding in the database
     * matches where the user_id is equal to the specified user from the authentication token
     * @param authentication --> the authentication to be passed in 
     * @return --> A map of strings and objects. It includes one entry of "matches" with a list of matches, as well as the entry of
     * "totalMatches" with the count of all the matches the user has played
     */
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

    /**
     * This is the method that is called to retrieve stats for a character.
     * It can be called 2 ways:
     * 1. With just the character name, which will return the overall stats for that character
     * 2. With both the character name and the opponent character, which will return the matchup stats for those 2 characters
     * @param characterName --> the character to get the stats for
     * @param opponentCharacter --> the opponent character to get the matchup stats for
     * @param authentication --> the authentication to be passed in
     * @return --> the stats for the character
     */
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

    @DeleteMapping("/matches/{matchId}")
    public void deleteMatch(@PathVariable Long matchId) {
        matchService.deleteMatchById(matchId);
    }

    @PutMapping("/matches/{matchId}")
    public void updateMatch(@PathVariable Long matchId, @RequestBody Match match) {
        matchService.updateMatch(matchId, match.getUserCharacter(), match.getOpponentCharacter(), match.getResult());
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
