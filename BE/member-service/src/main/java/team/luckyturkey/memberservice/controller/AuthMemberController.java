package team.luckyturkey.memberservice.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class AuthMemberController {


    @GetMapping("/main")
    public String mainPage(){
        return "main";
    }
    @GetMapping("/my")
    public String myPage(){
        return "my";
    }
}
