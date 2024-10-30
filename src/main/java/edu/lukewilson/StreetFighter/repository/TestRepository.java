package edu.lukewilson.StreetFighter.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import edu.lukewilson.StreetFighter.model.Test;

public interface TestRepository extends JpaRepository<Test, Long> {
}
