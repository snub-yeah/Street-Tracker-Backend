package edu.lukewilson.StreetFighter.service;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import edu.lukewilson.StreetFighter.model.Test;
import edu.lukewilson.StreetFighter.repository.TestRepository;

@Service
public class TestService {
    private static final Logger logger = LoggerFactory.getLogger(TestService.class);

    @Autowired
    private TestRepository testRepository;
    //this entire thing can be deleted
    public List<Test> getAllTests() {
        List<Test> tests = testRepository.findAll();
        logger.info("Retrieved {} tests from the database", tests.size());
        for (Test test : tests) {
            logger.info("Test object: {}", test);
        }
        return tests;
    }
}
