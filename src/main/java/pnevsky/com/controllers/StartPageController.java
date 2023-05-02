package pnevsky.com.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class StartPageController {
    @GetMapping("/")
    public String books(){
        return "start";
    }

}
