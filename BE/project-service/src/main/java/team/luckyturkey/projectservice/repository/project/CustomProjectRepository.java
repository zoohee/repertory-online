package team.luckyturkey.projectservice.repository.project;

import team.luckyturkey.projectservice.document.Project;

public interface CustomProjectRepository {
    Project saveWithSequence(Project project);
    Project findByModify(Project project);
}
