package team.luckyturkey.projectservice.controller.configuration;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import team.luckyturkey.projectservice.controller.ProjectController;
import team.luckyturkey.projectservice.document.Project;
import team.luckyturkey.projectservice.service.ProjectService;

import java.util.List;

@Configuration
public class TestWebConfig {

    private static class MockProjectService implements ProjectService{
        @Override
        public void updateProject(Long projectId, List<Long> sourceIdList) {

        }

        @Override
        public Project findProject(Long projectId) {
            return null;
        }
    }

    @Bean
    public ProjectService projectService(){
        return new MockProjectService();
    }

    @Bean
    public ProjectController projectController(SimpMessagingTemplate simpMessagingTemplate, ProjectService projectService){
        return new ProjectController(projectService, simpMessagingTemplate);
    }
}

