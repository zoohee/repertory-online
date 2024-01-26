package team.luckyturkey.projectservice.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.messaging.handler.annotation.DestinationVariable;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import team.luckyturkey.projectservice.controller.requestdto.PatchProjectRequestDto;
import team.luckyturkey.projectservice.controller.requestdto.ProjectUpdateRequestDto;
import team.luckyturkey.projectservice.controller.requestdto.SaveProjectRequestDto;
import team.luckyturkey.projectservice.document.Project;
import team.luckyturkey.projectservice.service.ProjectService;

import java.time.Instant;
import java.util.ArrayList;

@Slf4j
@RestController
@RequiredArgsConstructor
@Controller
public class ProjectController {


    private final ProjectService projectService;
    /**
     * @author  Sungho Lee
     * @param   projectId Long
     * @param   projectData List<Long>
     */
    @MessageMapping("/message/{projectId}")
    public void receiveProjectData(
            @DestinationVariable Long projectId,
             @Payload ProjectUpdateRequestDto projectData){

        projectService.updateProjectSourceList(projectId, projectData.getSourceIdList());
    }

    @ResponseBody
    @GetMapping("/{projectId}")
    public ResponseEntity<Project> getProject(
            @PathVariable Long projectId){

        Project project = projectService.findProject(projectId);
        return ResponseEntity.ok(project);
    }

    @ResponseBody
    @PatchMapping("/{projectId}")
    public ResponseEntity<Long> patchProject(
            @PathVariable Long projectId,
            @RequestBody PatchProjectRequestDto patchProjectRequestDto
    ){
        Project project = Project.builder()
                .id(projectId)
                .projectName(patchProjectRequestDto.getProjectName())
                .build();

        projectService.updateProjectSetting(project);
        return ResponseEntity.ok(projectId);
    }

    @ResponseBody
    @PostMapping
    public ResponseEntity<Long> saveProject(
            @RequestBody SaveProjectRequestDto saveProjectRequestDto
    ){
        //todo: user id 삽입 기능 필요
        Project project = Project.builder()
                .projectName(saveProjectRequestDto.getProjectName())
                .projectDate(Instant.now())
                .sourceList(new ArrayList<>())
                .build();

        Long projectId = projectService.saveProject(project, saveProjectRequestDto.getProjectThumbnail());
        return ResponseEntity.ok(projectId);
    }
}
