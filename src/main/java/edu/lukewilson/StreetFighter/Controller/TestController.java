package edu.lukewilson.StreetFighter.Controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import edu.lukewilson.StreetFighter.service.TestService;
import edu.lukewilson.StreetFighter.model.Test;
import java.util.List;

@RestController
public class TestController {
    @Autowired
    private TestService testService;

    @GetMapping("/api/tests")
    public List<Test> getAllTests() {
        return testService.getAllTests();
    }
}
