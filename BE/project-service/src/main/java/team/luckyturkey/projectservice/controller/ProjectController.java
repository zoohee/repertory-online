package team.luckyturkey.projectservice.controller;

import org.springframework.messaging.handler.annotation.DestinationVariable;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import team.luckyturkey.projectservice.controller.requestdto.ProjectUpdateRequestDto;

@RestController
public class ProjectController {

    @GetMapping("/test")
    public String test() {
        return "project test";
    }

    @MessageMapping("/message/{projectId}")
    @SendTo("/consume")
    public void sendProjectData(
            @DestinationVariable Long projectId,
            @Payload ProjectUpdateRequestDto projectData){
    }
}
