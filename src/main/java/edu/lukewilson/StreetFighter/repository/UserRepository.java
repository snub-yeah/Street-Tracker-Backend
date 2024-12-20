package edu.lukewilson.StreetFighter.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import edu.lukewilson.StreetFighter.model.User;

public interface UserRepository extends JpaRepository<User, Long> {
    //this returns an optional because there's a chance that the user doesn't already exist
    Optional<User> findByAuth0Id(String auth0Id);
}
