package team.luckyturkey.projectservice.controller;

import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.messaging.handler.annotation.DestinationVariable;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import team.luckyturkey.projectservice.controller.requestdto.PatchProjectRequestDto;
import team.luckyturkey.projectservice.controller.requestdto.ProjectUpdateRequestDto;
import team.luckyturkey.projectservice.document.Project;
import team.luckyturkey.projectservice.service.JwtUtil;
import team.luckyturkey.projectservice.service.ProjectService;

import java.time.Instant;
import java.util.ArrayList;
import java.util.List;

@Slf4j
@RequiredArgsConstructor
@Controller
//todo: userid 기반, 해당 사용자의 프로젝트 목록을 모두 가져오는 api 개발 필요
public class ProjectController {

    private final ProjectService projectService;
    private final JwtUtil jwtUtil;

    @Deprecated
    @GetMapping("/test")
    public ResponseEntity<String> test(HttpServletRequest request){
        Long memberId = Long.parseLong(request.getHeader("memberId"));
        return ResponseEntity.ok(memberId.toString());
    }

    @GetMapping("/")
    public ResponseEntity<List<Project>> getMyProjects() {
        // TODO: memberID <- jwt
        Long memberId = 1L;
        List<Project> response = projectService.getProjectList(memberId);
        return ResponseEntity.ok(response);
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
    public ResponseEntity<Long> postProject(
            @RequestPart String projectName,
            @RequestPart MultipartFile projectThumbnail,
            @RequestHeader("Authorization") final String accessToken
    ){

        String atc = accessToken.split(" ")[1];

        Project project = Project.builder()
                .memberId(jwtUtil.getMemberId(atc))
                .projectName(projectName)
                .projectDate(Instant.now())
                .sourceList(new ArrayList<>())
//                .memberId(memberId)
                .build();

        Long projectId = projectService.saveProject(project, projectThumbnail);
        return ResponseEntity.ok(projectId);
    }

    @ResponseBody
    @DeleteMapping("/{projectId}")
    public ResponseEntity<Long> deleteProject(
            @PathVariable Long projectId
    ){
        // TODO: 아이디 없을 때 Exception 처리
        projectService.deleteProject(projectId);
        return ResponseEntity.ok(projectId);
    }
}
