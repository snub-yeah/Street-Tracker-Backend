package edu.lukewilson.StreetFighter.repository;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import edu.lukewilson.StreetFighter.model.Match;
import edu.lukewilson.StreetFighter.model.User;

public interface MatchRepository extends JpaRepository<Match, Long> {
    List<Match> findByUserOrderByMatchTimestampDesc(User user);

    //when adding a match, this is the query that inserts
    //INSERT INTO matches (user_id, user_character, opponent_character, result, match_timestamp) 
    //VALUES (?, ?, ?, ?, ?)
    @Modifying
    @Query(value = "INSERT INTO matches (user_id, user_character, opponent_character, result, match_timestamp) VALUES (?1, ?2, ?3, ?4, ?5)", nativeQuery = true)
    void addMatch(Long userId, String userCharacter, String opponentCharacter, int result, LocalDateTime matchTimestamp);
    
    //count matches by user id
    @Query(value = "SELECT COUNT(*) as TotalMatches FROM matches WHERE user_id = ?1", nativeQuery = true)
    Long countMatchesByUserId(Long userId);

    @Modifying
    @Query(value = "DELETE FROM matches WHERE match_id = ?1", nativeQuery = true)
    void deleteMatchById(Long matchId);

    @Modifying
    @Query(value = "UPDATE matches SET user_character=?2, opponent_character=?3, result=?4 WHERE match_id=?1", nativeQuery = true)
    void updateMatch(Long matchId, String userCharacter, String opponentCharacter, int result);


}
