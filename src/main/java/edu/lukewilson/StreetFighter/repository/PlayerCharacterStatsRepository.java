package edu.lukewilson.StreetFighter.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import edu.lukewilson.StreetFighter.model.PlayerCharacterStats;
import edu.lukewilson.StreetFighter.model.User;

public interface PlayerCharacterStatsRepository extends JpaRepository<PlayerCharacterStats, Long> {
    PlayerCharacterStats findByUserAndPlayerCharacterAndOpponentCharacter(
        User user, String playerCharacter, String opponentCharacter);

    @Query(value = """
        SELECT 0 as stats_id, pcs.user_id, ?2 as player_character, NULL as opponent_character, 
               SUM(wins) as wins, SUM(losses) as losses 
        FROM player_character_stats pcs 
        WHERE user_id = ?1 AND player_character = ?2""", nativeQuery = true)
    PlayerCharacterStats findByUserAndPlayerCharacter(Long userId, String playerCharacter);

    @Query(value = """
        SELECT 0 as stats_id, pcs.user_id, ?2 as player_character, ?3 as opponent_character, 
               SUM(wins) as wins, SUM(losses) as losses 
        FROM player_character_stats pcs 
        WHERE user_id = ?1 AND player_character = ?2 AND opponent_character = ?3""", nativeQuery = true)
    PlayerCharacterStats findByUserAndPlayerCharacterAndOpponentCharacter(Long userId, String playerCharacter, String opponentCharacter);
} 