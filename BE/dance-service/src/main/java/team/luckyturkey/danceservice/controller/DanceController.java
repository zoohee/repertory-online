package team.luckyturkey.danceservice.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class DanceController {
    @GetMapping("/test")
    public String test() {
        return "dance test";
    }
}
