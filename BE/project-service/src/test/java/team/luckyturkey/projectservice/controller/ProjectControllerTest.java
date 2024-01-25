package team.luckyturkey.projectservice.controller;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import team.luckyturkey.projectservice.service.ProjectService;


@SpringBootTest
public class ProjectControllerTest {

    @MockBean private ProjectService projectService;
    @Autowired private ProjectController projectController;

    @Test
    void getProjectTest(){

    }

}
