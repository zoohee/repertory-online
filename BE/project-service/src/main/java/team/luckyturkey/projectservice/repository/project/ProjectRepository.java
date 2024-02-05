package team.luckyturkey.projectservice.repository.project;


import org.springframework.data.mongodb.repository.MongoRepository;
import team.luckyturkey.projectservice.document.Project;

public interface ProjectRepository extends MongoRepository<Project, Long>, CustomProjectRepository {
}
