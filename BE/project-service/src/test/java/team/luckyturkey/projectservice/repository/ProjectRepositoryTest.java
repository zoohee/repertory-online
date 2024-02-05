package team.luckyturkey.projectservice.repository;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.mongodb.core.MongoTemplate;
import team.luckyturkey.projectservice.document.Project;
import team.luckyturkey.projectservice.repository.project.ProjectRepository;

import java.time.ZonedDateTime;
import java.util.List;
import java.util.Optional;

@SpringBootTest
public class ProjectRepositoryTest {

    @Autowired private ProjectRepository projectRepository;
    @Autowired private MongoTemplate mongoTemplate;

    @BeforeEach
    void before(){
        mongoTemplate.getDb().drop();
    }

    @Test
    void save(){
        Project windmillProject = Project.builder()
                .projectDate(ZonedDateTime.now().toInstant())
                .userId(1L)
                .projectName("windmill project")
                .projectThumbnailUrl("s3//windmill.video")
                .sourceList(List.of(1L, 2L, 3L, 4L))
                .build();

        Project headspinProjectProject = Project.builder()
                .projectDate(ZonedDateTime.now().toInstant())
                .userId(1L)
                .projectName("headspin project")
                .projectThumbnailUrl("s3//headspin.video")
                .sourceList(List.of(2L, 3L, 4L, 5L))
                .build();

        Project freezeProject = Project.builder()
                .projectDate(ZonedDateTime.now().toInstant())
                .userId(2L)
                .projectName("freeze project")
                .projectThumbnailUrl("s3//freeze.video")
                .sourceList(List.of(6L, 7L, 8L))
                .build();


        Project savedWindmill = projectRepository.saveWithSequence(windmillProject);
        Project savedHeadspin = projectRepository.saveWithSequence(headspinProjectProject);
        Project savedFreeze = projectRepository.saveWithSequence(freezeProject);

        Optional<Project> foundWindmill = projectRepository.findById(1L);
        Optional<Project> foundHeadspin = projectRepository.findById(2L);
        Optional<Project> foundFreeze = projectRepository.findById(3L);

        Assertions.assertThat(savedWindmill)
                        .usingRecursiveComparison()
                        .ignoringFields("projectDate")
                        .isEqualTo(foundWindmill.get());

        Assertions.assertThat(savedHeadspin)
                .usingRecursiveComparison()
                .ignoringFields("projectDate")
                .isEqualTo(foundHeadspin.get());

        Assertions.assertThat(savedFreeze)
                .usingRecursiveComparison()
                .ignoringFields("projectDate")
                .isEqualTo(foundFreeze.get());

    }

    @Test
    void update(){
        Project windmillProject = Project.builder()
                .projectDate(ZonedDateTime.now().toInstant())
                .userId(1L)
                .projectName("windmill project")
                .projectThumbnailUrl("s3//windmill.video")
                .sourceList(List.of(1L, 2L, 3L, 4L))
                .build();


        Project savedWindmill = projectRepository.saveWithSequence(windmillProject);

        Project changedWindmill = Project.builder()
                .id(savedWindmill.getId())
                .sourceList(List.of(5L, 6L, 7L))
                .build();

        Project modifiedProject = projectRepository.findAndUpdateSourceList(changedWindmill.getId(), changedWindmill.getSourceList());
        Optional<Project> foundWindmill = projectRepository.findById(savedWindmill.getId());

        Assertions.assertThat(modifiedProject)
                .usingRecursiveComparison()
                .ignoringFields("projectDate")
                .isEqualTo(foundWindmill.get());

        Assertions.assertThat(windmillProject)
                .usingRecursiveComparison()
                .ignoringFields("projectDate")
                .ignoringFields("sourceList")
                .isEqualTo(foundWindmill.get());

        Assertions.assertThat(foundWindmill.get().getSourceList())
                .isEqualTo(List.of(5L, 6L, 7L));
    }
}
