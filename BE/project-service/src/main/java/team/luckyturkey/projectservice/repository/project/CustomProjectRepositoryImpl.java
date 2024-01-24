package team.luckyturkey.projectservice.repository.project;

import lombok.RequiredArgsConstructor;
import org.springframework.data.mongodb.core.MongoTemplate;
import team.luckyturkey.projectservice.document.Project;
import team.luckyturkey.projectservice.repository.SequenceGenerator;

@RequiredArgsConstructor
public class CustomProjectRepositoryImpl implements CustomProjectRepository{

    private final MongoTemplate mongoTemplate;
    private final SequenceGenerator sequenceGenerator;
    @Override
    public Project saveWithSequence(Project project) {

        Long nextSequence = sequenceGenerator.generateSequence(Project.SEQUENCE_NAME);
        project.setId(nextSequence);

        return mongoTemplate.save(project);
    }
}
