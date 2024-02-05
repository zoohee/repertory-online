package team.luckyturkey.memberservice.member.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@Controller
public class AuthMemberController {
    @GetMapping("/login/OAuth")
    public String loginPage(){
        return "oauth-login";
    }

}
