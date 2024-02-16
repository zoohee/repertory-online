package team.luckyturkey.projectservice.repository.project;

import lombok.RequiredArgsConstructor;
import org.springframework.data.mongodb.core.FindAndModifyOptions;
import org.springframework.data.mongodb.core.MongoTemplate;
import team.luckyturkey.projectservice.document.Project;
import team.luckyturkey.projectservice.repository.SequenceGenerator;

import java.util.List;

import static org.springframework.data.mongodb.core.query.Criteria.where;
import static org.springframework.data.mongodb.core.query.Query.query;
import static org.springframework.data.mongodb.core.query.Update.update;

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

    @Override
    public Project findAndUpdateSourceList(Long projectId, List<Long> sourceIdList) {
        return mongoTemplate.findAndModify(query(where("_id").is(projectId)),
                update("sourceList", sourceIdList),
                FindAndModifyOptions.options().returnNew(true),
                Project.class);
    }

    @Override
    public Project findAndUpdateDetails(Project project) {
        return mongoTemplate.findAndModify(query(where("_id").is(project.getId())),
                update("projectName", project.getProjectName()),
                FindAndModifyOptions.options().returnNew(true),
                Project.class);
    }

    @Override
    public List<Project> findByUserId(Long userId) {
        return mongoTemplate.find(query(where("memberId").is(userId)),
                Project.class);
    }
}
