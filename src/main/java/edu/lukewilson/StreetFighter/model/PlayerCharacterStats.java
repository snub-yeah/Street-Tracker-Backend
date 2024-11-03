package edu.lukewilson.StreetFighter.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

@Entity
@Table(name = "player_character_stats")
public class PlayerCharacterStats {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long stats_id;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    @Column(name = "player_character")
    private String playerCharacter;

    @Column(name = "opponent_character")
    private String opponentCharacter;

    private Integer wins;
    private Integer losses;

    public Long getStats_id() {
        return stats_id;
    }

    public void setStats_id(Long stats_id) {
        this.stats_id = stats_id;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public String getPlayerCharacter() {
        return playerCharacter;
    }

    public void setPlayerCharacter(String playerCharacter) {
        this.playerCharacter = playerCharacter;
    }

    public String getOpponentCharacter() {
        return opponentCharacter;
    }

    public void setOpponentCharacter(String opponentCharacter) {
        this.opponentCharacter = opponentCharacter;
    }

    public Integer getWins() {
        return wins;
    }

    public void setWins(Integer wins) {
        this.wins = wins;
    }

    public Integer getLosses() {
        return losses;
    }

    public void setLosses(Integer losses) {
        this.losses = losses;
    }
} 