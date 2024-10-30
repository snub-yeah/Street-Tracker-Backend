package edu.lukewilson.StreetFighter.model;

import java.time.LocalDateTime;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

@Entity
@Table(name = "matches")
public class Match {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long match_id;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    @Column(name = "user_character")
    private String userCharacter;

    @Column(name = "opponent_character")
    private String opponentCharacter;

    private Integer result;

    @Column(name = "match_timestamp")
    private LocalDateTime matchTimestamp;

    // Getters and setters

    public Long getMatch_id() {
        return match_id;
    }

    public void setMatch_id(Long match_id) {
        this.match_id = match_id;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public String getUserCharacter() {
        return userCharacter;
    }

    public void setUserCharacter(String userCharacter) {
        this.userCharacter = userCharacter;
    }

    public String getOpponentCharacter() {
        return opponentCharacter;
    }

    public void setOpponentCharacter(String opponentCharacter) {
        this.opponentCharacter = opponentCharacter;
    }

    public Integer getResult() {
        return result;
    }

    public void setResult(Integer result) {
        this.result = result;
    }

    public LocalDateTime getMatchTimestamp() {
        return matchTimestamp;
    }

    public void setMatchTimestamp(LocalDateTime matchTimestamp) {
        this.matchTimestamp = matchTimestamp;
    }
}
