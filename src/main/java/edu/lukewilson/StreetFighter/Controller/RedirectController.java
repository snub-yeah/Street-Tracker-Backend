package edu.lukewilson.StreetFighter.Controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class RedirectController {

    @GetMapping("/redirectToFrontend")
    public String redirectToFrontend() {
        // redirect the user to the frontend's login page. i can't even remember if this is being used lmao
        return "redirect:http://localhost:3000/login";
    }
}

