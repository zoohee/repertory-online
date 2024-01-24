package team.luckyturkey.projectservice.repository;


import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;
import team.luckyturkey.projectservice.document.Project;

@Repository
public interface ProjectRepository extends MongoRepository<Project, Long> {
}
