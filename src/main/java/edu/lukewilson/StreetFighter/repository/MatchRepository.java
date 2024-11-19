package edu.lukewilson.StreetFighter.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import edu.lukewilson.StreetFighter.model.Match;
import edu.lukewilson.StreetFighter.model.User;

public interface MatchRepository extends JpaRepository<Match, Long> {
    List<Match> findByUserOrderByMatchTimestampDesc(User user);
    
    //count matches by user id
    @Query(value = "SELECT COUNT(*) as TotalMatches FROM matches WHERE user_id = ?1", nativeQuery = true)
    Long countMatchesByUserId(Long userId);

    @Modifying
    @Query(value = "DELETE FROM matches WHERE match_id = ?1", nativeQuery = true)
    void deleteMatchById(Long matchId);
}
