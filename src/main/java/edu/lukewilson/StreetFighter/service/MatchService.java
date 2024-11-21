package edu.lukewilson.StreetFighter.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import edu.lukewilson.StreetFighter.model.Match;
import edu.lukewilson.StreetFighter.model.PlayerCharacterStats;
import edu.lukewilson.StreetFighter.model.User;
import edu.lukewilson.StreetFighter.repository.MatchRepository;
import edu.lukewilson.StreetFighter.repository.PlayerCharacterStatsRepository;

@Service
public class MatchService {
    @Autowired
    private MatchRepository matchRepository;

    @Autowired
    private PlayerCharacterStatsRepository statsRepository;

    @Transactional
    public Match addMatch(Match match) {
        // save the match
        Match savedMatch = matchRepository.save(match);
        
        updateCharacterStats(match);
        
        return savedMatch;
    }

    private void updateCharacterStats(Match match) {
        // find existing stats or create new ones
        PlayerCharacterStats stats = statsRepository.findByUserAndPlayerCharacterAndOpponentCharacter(
            match.getUser(), 
            match.getUserCharacter(), 
            match.getOpponentCharacter()
        );

        if (stats == null) {
            // if the stats don't exist, create new ones
            // this is basically like INSERT INTO player_character_stats VALUES (..., match.getUser(), match.getUserCharacter(), match.getOpponentCharacter(), 0, 0)
            stats = new PlayerCharacterStats();
            stats.setUser(match.getUser());
            stats.setPlayerCharacter(match.getUserCharacter());
            stats.setOpponentCharacter(match.getOpponentCharacter());
            stats.setWins(0);
            stats.setLosses(0);
        }

        // update wins/losses based on match result
        // 1 and 2 mean win, 3 and 4 mean loss
        if (match.getResult() == 1 || match.getResult() == 2) {
            stats.setWins(stats.getWins() + 1);
        } else if (match.getResult() == 3 || match.getResult() == 4) {
            stats.setLosses(stats.getLosses() + 1);
        }

        statsRepository.save(stats);
    }

    public List<Match> getMatchesByUser(User user) {
        return matchRepository.findByUserOrderByMatchTimestampDesc(user);
    }

    public Long getMatchCountByUser(User user) {
        return matchRepository.countMatchesByUserId(user.getId());
    }

    @Transactional
    public void deleteMatchById(Long matchId) {
        matchRepository.deleteMatchById(matchId);
    }

    @Transactional
    public void updateMatch(Long matchId, String userCharacter, String opponentCharacter, int result) {
        matchRepository.updateMatch(matchId, userCharacter, opponentCharacter, result);
    }
}
