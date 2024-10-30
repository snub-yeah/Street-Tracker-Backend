package edu.lukewilson.StreetFighter.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import edu.lukewilson.StreetFighter.model.PlayerCharacterStats;
import edu.lukewilson.StreetFighter.model.User;

public interface PlayerCharacterStatsRepository extends JpaRepository<PlayerCharacterStats, Long> {
    PlayerCharacterStats findByUserAndPlayerCharacterAndOpponentCharacter(
        User user, String playerCharacter, String opponentCharacter);
} 