package team.luckyturkey.projectservice.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.messaging.handler.annotation.DestinationVariable;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import team.luckyturkey.projectservice.controller.requestdto.ProjectUpdateRequestDto;
import team.luckyturkey.projectservice.document.Project;
import team.luckyturkey.projectservice.service.ProjectService;

@Slf4j
@RestController
@RequiredArgsConstructor
@Controller
public class ProjectController {


    private final ProjectService projectService;
    @Deprecated
    @GetMapping("/test")
    public String test() {
        return "project test";
    }


    /**
     * @author  Sungho Lee
     * @param   projectId Long
     * @param   projectData List<Long>
     */
    @MessageMapping("/message/{projectId}")
    public void receiveProjectData(
            @DestinationVariable Long projectId,
             @Payload ProjectUpdateRequestDto projectData){

        projectService.updateProject(projectId, projectData.getSourceIdList());
        throw new RuntimeException();
    }

    @ResponseBody
    @GetMapping("/{projectId}")
    public ResponseEntity<?> getProject(
            @PathVariable Long projectId){

        Project project = projectService.findProject(projectId);
        return ResponseEntity.ok(project);
    }

}
